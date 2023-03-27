package io.grayproject.nwha.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ilya Avkhimenya
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class BadRefreshTokenException extends RuntimeException {

    public BadRefreshTokenException() {
        super("Token not verified, you need to sign in!");
    }
}
