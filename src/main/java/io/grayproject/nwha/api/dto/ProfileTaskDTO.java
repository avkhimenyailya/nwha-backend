package io.grayproject.nwha.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProfileTaskDTO(Long id,
                             TaskDTO task,
                             List<AnswerDTO> answers) {
}