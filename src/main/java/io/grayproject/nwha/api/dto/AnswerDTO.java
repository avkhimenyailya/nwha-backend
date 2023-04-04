package io.grayproject.nwha.api.dto;

import lombok.Builder;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record AnswerDTO(Long id,
                        Long optionId,
                        Long questionId,
                        Long profileTaskId) {
}