package io.grayproject.nwha.api.dto.authentication;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author Ilya Avkhimenya
 */
public record RegisterRequest(
        @NotBlank @Size(min = 3) String username,
        @NotBlank @Size(min = 6) String password,
        @NotBlank String invitationCode) {
}