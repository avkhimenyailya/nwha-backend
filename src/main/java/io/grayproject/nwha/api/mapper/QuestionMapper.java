package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Question;
import io.grayproject.nwha.api.dto.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class QuestionMapper implements Function<Question, QuestionDTO> {
    private final OptionMapper optionMapper;

    @Autowired
    public QuestionMapper(OptionMapper optionMapper) {
        this.optionMapper = optionMapper;
    }

    @Override
    public QuestionDTO apply(Question question) {
        return QuestionDTO
                .builder()
                .id(question.getId())
                .ordinalNumber(question.getOrdinalNumber())
                .options(question
                        .getOptions()
                        .stream()
                        .map(optionMapper)
                        .toList())
                .build();
    }
}
