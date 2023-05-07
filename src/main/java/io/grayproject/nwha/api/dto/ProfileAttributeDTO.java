package io.grayproject.nwha.api.dto;

import lombok.Builder;

@Builder
public record ProfileAttributeDTO(Integer value,
                                  Long profileId,
                                  String attributeName) {
}
