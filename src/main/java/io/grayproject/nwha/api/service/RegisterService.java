package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.authentication.LoginRequest;
import io.grayproject.nwha.api.dto.authentication.RegisterRequest;

/**
 * @author Ilya Avkhimenya
 */
public interface RegisterService {

    LoginRequest register(RegisterRequest registerRequest);
}
