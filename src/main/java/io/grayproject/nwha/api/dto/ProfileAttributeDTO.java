package io.grayproject.nwha.api.dto;

import lombok.Builder;

@Builder
public record ProfileAttributeDTO(String traitName,
                                  Long profileId,
                                  Integer value) {
}
