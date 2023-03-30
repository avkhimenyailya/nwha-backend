package io.grayproject.nwha.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProfileTaskDTO(Long id,
                             Long taskId,
                             List<AnswerDTO> answers) {
}

// { id: 3, taskId: 1, answers: [ { id: 3245, optionId: 2, profileTaskId: 2 } ] },