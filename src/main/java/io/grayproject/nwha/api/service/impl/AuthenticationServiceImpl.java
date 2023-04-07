package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.RefreshToken;
import io.grayproject.nwha.api.dto.authentication.AuthResponse;
import io.grayproject.nwha.api.dto.authentication.LoginRequest;
import io.grayproject.nwha.api.dto.authentication.RefreshTokenRequest;
import io.grayproject.nwha.api.dto.authentication.RegisterRequest;
import io.grayproject.nwha.api.exception.BadRefreshTokenException;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.repository.RefreshTokenRepository;
import io.grayproject.nwha.api.security.util.JwtTokenUtils;
import io.grayproject.nwha.api.service.AuthenticationService;
import io.grayproject.nwha.api.service.RegisterService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private final RefreshTokenRepository refreshTokenRepository;
    private final ProfileRepository profileRepository;

    private final RegisterService registerService;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,
                                     UserDetailsService userDetailsService,
                                     RefreshTokenRepository refreshTokenRepository,
                                     ProfileRepository profileRepository,
                                     RegisterService registerService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.profileRepository = profileRepository;
        this.registerService = registerService;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        log.info("Attempt to register for {}", registerRequest.username());
        LoginRequest beforeRegister = registerService.register(registerRequest);
        return login(beforeRegister);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        log.info("Attempt to authenticate for {}", loginRequest.username());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

        Authentication authentication = authenticationManager
                .authenticate(authenticationToken);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        return buildAuthResponse((UserDetails) authentication.getPrincipal());
    }

    @Override
    @Transactional
    public AuthResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest) {
        log.info("Attempt to refresh access token {}", refreshTokenRequest.refreshToken());

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(refreshTokenRequest.refreshToken())
                .orElseThrow(BadRefreshTokenException::new);

        if (JwtTokenUtils.verifyJwtToken(refreshToken.getToken())) {
            String username = refreshToken.getUser().getUsername();
            refreshTokenRepository.deleteById(refreshToken.getId());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return buildAuthResponse(userDetails);
        } else {
            throw new BadRefreshTokenException();
        }
    }

    public AuthResponse buildAuthResponse(UserDetails userDetails) {
        log.info("Authentication successful, generating tokens...");
        Profile profile = profileRepository
                .findProfileByUserUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Fatal error!"));

        String accessTokenString = JwtTokenUtils.generateAccessToken(userDetails.getUsername());
        String refreshTokenString = JwtTokenUtils.generateRefreshToken(userDetails.getUsername());

        refreshTokenRepository
                .findRefreshTokenByUser(profile.getUser())
                .ifPresent(refreshToken -> refreshTokenRepository.deleteById(refreshToken.getId()));

        RefreshToken refreshToken = RefreshToken
                .builder()
                .user(profile.getUser())
                .token(refreshTokenString)
                .build();
        refreshTokenRepository.save(refreshToken);

        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Long profileId = profileRepository
                .findProfileByUserUsername(userDetails.getUsername())
                .map(Profile::getId)
                .orElse(null);

        return AuthResponse
                .builder()
                .accessToken(accessTokenString)
                .refreshToken(refreshTokenString)
                .username(userDetails.getUsername())
                .build();
    }
}
