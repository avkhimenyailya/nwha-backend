package io.grayproject.nwha.api.dto;

import lombok.Builder;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Builder
public record QuestionDTO(Long id,
                          Integer ordinalNumber,
                          List<OptionDTO> options) {
}
