package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Answer;
import io.grayproject.nwha.api.domain.ProfileTask;
import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
@RequiredArgsConstructor
public class ProfileTaskMapper implements Function<ProfileTask, ProfileTaskDTO> {
    private final AnswerMapper answerMapper;

    @Override
    public ProfileTaskDTO apply(ProfileTask profileTask) {
        return ProfileTaskDTO
                .builder()
                .id(profileTask.getId())
                .taskId(profileTask.getTask().getId())
                .answers(getAnswers(profileTask.getAnswers()))
                .build();
    }

    private List<AnswerDTO> getAnswers(List<Answer> answers) {
        return answers.stream().map(answerMapper).toList();
    }
}
