package io.grayproject.nwha.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CollectionThingsDTO(Long id,
                                  Long profileId,
                                  String name,
                                  List<ThingDTO> things) {
}
