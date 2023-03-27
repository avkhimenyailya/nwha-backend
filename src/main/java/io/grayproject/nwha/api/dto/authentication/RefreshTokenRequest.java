package io.grayproject.nwha.api.dto.authentication;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Ilya Avkhimenya
 */
public record RefreshTokenRequest(
        @NotBlank String refreshToken) {
}