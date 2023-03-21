package io.grayproject.nwha.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProfileDTO(Long id,
                         String description,
                         List<ProfileTaskDTO> profileTasks,
                         List<ProfileTraitDTO> profileTraits) {
}
