package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import io.grayproject.nwha.api.entity.Answer;
import io.grayproject.nwha.api.entity.ProfileTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private Set<AnswerDTO> getAnswers(Set<Answer> answers) {
        return answers.stream().map(answerMapper).collect(Collectors.toSet());
    }
}
