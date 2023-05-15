package io.grayproject.nwha.api.mapper;

import io.grayproject.nwha.api.domain.Answer;
import io.grayproject.nwha.api.domain.ProfileTask;
import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import io.grayproject.nwha.api.dto.ThingDTO;
import io.grayproject.nwha.api.dto.ThingDTO2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Ilya Avkhimenya
 */
@Component
@RequiredArgsConstructor
public class ProfileTaskMapper implements Function<ProfileTask, ProfileTaskDTO> {
    private final TaskMapper taskMapper;
    private final ThingMapper thingMapper;
    private final ThingMapper2 thingMapper2;
    private final AnswerMapper answerMapper;

    @Override
    public ProfileTaskDTO apply(ProfileTask profileTask) {
        ThingDTO thingDTO =
                Optional.ofNullable(profileTask.getThings())
                        .flatMap(things -> things
                                .stream()
                                .filter(thing -> !thing.isRemoved() && !thing.isArchived())
                                .findFirst()
                                .map(thingMapper))
                        .orElse(null);

        ThingDTO2 thingDTO2 =
                Optional.ofNullable(profileTask.getThings())
                        .flatMap(things -> things
                                .stream()
                                .filter(thing -> !thing.isRemoved() && !thing.isArchived())
                                .findFirst()
                                .map(thingMapper2))
                        .orElse(null);

        return ProfileTaskDTO
                .builder()
                .id(profileTask.getId())
                .profileId(profileTask.getProfile().getId())
                .thing(thingDTO)
                .thing2(thingDTO2)
                .task(taskMapper.apply(profileTask.getTask()))
                .answers(getAnswers(profileTask.getAnswers()))
                .build();
    }

    private List<AnswerDTO> getAnswers(List<Answer> answers) {
        return answers.stream().map(answerMapper).toList();
    }
}
