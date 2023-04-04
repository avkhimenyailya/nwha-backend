package io.grayproject.nwha.api.dto;

import lombok.Builder;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record TaskDTO(Long id,
                      Integer ordinalNumber,
                      String description,
                      List<QuestionDTO> questions) {
}
