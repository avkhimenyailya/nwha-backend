package io.grayproject.nwha.api.dto.register;

import lombok.Builder;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record RegisterRequestDTO(String username,
                                 String rawPassword,
                                 String invitationCode) {
}
