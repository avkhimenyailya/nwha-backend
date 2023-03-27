package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.authentication.AuthResponse;
import io.grayproject.nwha.api.dto.authentication.LoginRequest;
import io.grayproject.nwha.api.dto.authentication.RefreshTokenRequest;
import io.grayproject.nwha.api.dto.authentication.RegisterRequest;
import io.grayproject.nwha.api.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public AuthResponse signIn(
            HttpServletRequest httpServletRequest,
            @Validated @RequestBody LoginRequest loginRequest) {
        log.info("Authentication request from {}, ip: {}",
                loginRequest.username(), httpServletRequest.getRemoteAddr());
        return authenticationService.signIn(loginRequest);
    }

    @PostMapping("/register")
    public AuthResponse signUp(
            HttpServletRequest httpServletRequest,
            @Validated @RequestBody RegisterRequest registerRequest) {
        log.info("Register request from {}, ip: {}",
                registerRequest.username(), httpServletRequest.getRemoteAddr());
        return authenticationService.signUp(registerRequest);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshAccessToken(
            HttpServletRequest httpServletRequest,
            @Validated @RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Refresh token request, ip: {}", httpServletRequest.getRemoteAddr());
        return authenticationService.refreshAccessToken(refreshTokenRequest);
    }
}
