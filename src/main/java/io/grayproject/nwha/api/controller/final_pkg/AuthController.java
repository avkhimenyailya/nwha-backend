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

import static io.grayproject.nwha.api.util.ControllerPaths.AuthControllerPaths.*;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@RestController
@RequestMapping(CONTROLLER_PATH)
public class AuthController {
    private static final String LOG_LOGIN =     "Login request from {},     ip: {}";
    private static final String LOG_REGISTER =  "Register request from {},  ip: {}";
    private static final String LOG_REFRESH =   "Refresh request,           ip: {}";

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(POST_LOGIN)
    public AuthResponse login(@Validated @RequestBody LoginRequest loginRequest,
                              HttpServletRequest httpServletRequest) {
        log.info(LOG_LOGIN, loginRequest.username(), httpServletRequest.getRemoteAddr());
        return authenticationService.login(loginRequest);
    }

    @PostMapping(POST_REGISTER)
    public AuthResponse register(@Validated @RequestBody RegisterRequest registerRequest,
                                 HttpServletRequest httpServletRequest) {
        log.info(LOG_REGISTER, registerRequest.username(), httpServletRequest.getRemoteAddr());
        return authenticationService.register(registerRequest);
    }

    @PostMapping(POST_REFRESH)
    public AuthResponse refreshAccessToken(@Validated @RequestBody RefreshTokenRequest refreshTokenRequest,
                                           HttpServletRequest httpServletRequest) {
        log.info(LOG_REFRESH, httpServletRequest.getRemoteAddr());
        return authenticationService.refreshAccessToken(refreshTokenRequest);
    }
}
