package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.authentication.AuthResponse;
import io.grayproject.nwha.api.dto.authentication.LoginRequest;
import io.grayproject.nwha.api.dto.authentication.RefreshTokenRequest;
import io.grayproject.nwha.api.dto.authentication.RegisterRequest;

/**
 * @author Ilya Avkhimenya
 */
public interface AuthenticationService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest);
}