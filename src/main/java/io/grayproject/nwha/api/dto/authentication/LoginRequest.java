package io.grayproject.nwha.api.dto.authentication;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record LoginRequest(
        @NotBlank @Size(min = 3) String username,
        @NotBlank @Size(min = 6) String password) {
}