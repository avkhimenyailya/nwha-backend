package io.grayproject.nwha.api.dto;

import lombok.Builder;

@Builder
public record ProfileTraitDTO(String traitName,
                              Long profileId,
                              Integer value) {
}
