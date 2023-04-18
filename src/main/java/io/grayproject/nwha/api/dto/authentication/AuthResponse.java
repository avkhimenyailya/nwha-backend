package io.grayproject.nwha.api.dto.authentication;

import lombok.Builder;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record AuthResponse(Long profileId,
                           String username,
                           String accessToken,
                           String refreshToken) {
}