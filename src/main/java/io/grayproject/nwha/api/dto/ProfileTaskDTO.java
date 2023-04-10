package io.grayproject.nwha.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProfileTaskDTO(Long id,
                             Long profileId,
                             TaskDTO task,
                             ThingDTO thing,
                             List<AnswerDTO> answers) {
}