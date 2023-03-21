package io.grayproject.nwha.api.dto;

import lombok.Builder;

@Builder
public record ProfileTraitDTO(Long traitId,
                              Long profileId,
                              Integer value) {
}
