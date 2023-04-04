package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Answer;
import io.grayproject.nwha.api.dto.AnswerDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class AnswerMapper implements Function<Answer, AnswerDTO> {

    @Override
    public AnswerDTO apply(Answer answer) {
        return AnswerDTO
                .builder()
                .id(answer.getId())
                .optionId(answer.getOption().getId())
                .profileTaskId(answer.getProfileTask().getId())
                .questionId(answer.getOption().getQuestion().getId())
                .build();
    }
}
