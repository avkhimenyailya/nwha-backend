package io.grayproject.nwha.api.dto;

import lombok.Builder;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record ProfileDTO(Long id,
                         String username,
                         String description,
                         List<ProfileAttributeDTO> profileAttributes) {
}