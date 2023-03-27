package io.grayproject.nwha.api.dto.authentication;

import lombok.Builder;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record AuthResponse(
        Long profileId,
        String accessToken,
        String refreshToken,
        List<String> roles) {
}