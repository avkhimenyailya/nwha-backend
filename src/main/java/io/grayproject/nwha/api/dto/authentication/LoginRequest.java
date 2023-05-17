package io.grayproject.nwha.api.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record LoginRequest(
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 24, message = "Username must be between 3 and 24 characters long")
        @Pattern(regexp = "^[a-zA-Z0-9_-]{3,24}$",
                message = "Username must consist of letters, numbers, '-' and '_' characters, and be between 3 and 24 characters long")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters long")
        @Pattern(regexp = "^(?=.*\\d).{8,}$",
                message = "Password must be at least 8 characters long and contain at least one letter and one digit")
        String password) {
}