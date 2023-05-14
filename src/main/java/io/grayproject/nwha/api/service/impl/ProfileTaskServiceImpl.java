package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.*;
import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.ProfileTaskMapper;
import io.grayproject.nwha.api.repository.AnswerRepository;
import io.grayproject.nwha.api.repository.AnswerValueRepository;
import io.grayproject.nwha.api.repository.OptionRepository;
import io.grayproject.nwha.api.repository.ProfileTaskRepository;
import io.grayproject.nwha.api.service.ProfileService;
import io.grayproject.nwha.api.service.ProfileTaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ilya Avkhimenya
 */
@Service
@RequiredArgsConstructor
public class ProfileTaskServiceImpl implements ProfileTaskService {
    private final ProfileService profileService;
    private final ProfileTaskMapper profileTaskMapper;
    private final ProfileTaskRepository profileTaskRepository;
    private final AnswerValueRepository answerValueRepository;

    private final OptionRepository optionRepository;
    private final AnswerRepository answerRepository;


    @Override
    public ProfileTaskDTO getProfileTaskById(Long id) {
        return profileTaskRepository
                .findById(id)
                .map(profileTaskMapper)
                .orElseThrow(() -> new EntityNotFoundException(ProfileTask.class, id));
    }

    @Override
    public List<ProfileTaskDTO> getProfileTasksByProfileId(Long profileId) {
        return getProfileTasks(profileService.getProfileEntityById(profileId));
    }

    @Override
    public List<ProfileTaskDTO> getProfileTasksByPrincipal(Principal principal) {
        return getProfileTasks(profileService.getProfileEntityByPrincipal(principal));
    }

    @Override
    @Transactional
    public ProfileTaskDTO updateAnswers(Principal principal, Long profileTaskId, List<AnswerDTO> answers) {
        ProfileTask profileTask = getProfileTaskByPrincipalAndId(principal, profileTaskId);
        answerRepository.deleteAll(profileTask.getAnswers());


        List<Option> options = answers
                .stream()
                .map(a -> optionRepository.findById(a.optionId()).orElseThrow())
                .toList();

        List<Answer> answersEntities = options
                .stream()
                .map(option -> Answer
                        .builder()
                        .option(option)
                        .profileTask(profileTask)
                        .build())
                .toList();

        answerRepository.saveAll(answersEntities);
        profileTask.setAnswers(answersEntities);

        attributeRecalculation(profileTask.getProfile());
        return profileTaskMapper.apply(profileTask);
    }

    private List<ProfileTaskDTO> getProfileTasks(Profile profile) {
        return profile
                .getProfileTasks()
                .stream()
                .filter(profileTask -> !profileTask.getRemoved())
                .sorted(Comparator.comparing(profileTask -> profileTask.getTask().getOrdinalNumber()))
                .map(profileTaskMapper)
                .toList();
    }

    private ProfileTask getProfileTaskByPrincipalAndId(Principal principal, Long id) {
        return profileService
                .getProfileEntityByPrincipal(principal)
                .getProfileTasks()
                .stream()
                .filter(pt -> pt.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sorry, but you cannot update this task."));
    }

    private void attributeRecalculation(Profile profile) {
        Map<Long, Integer> scoreAttrs = new HashMap<>();
        List<Answer> answers = answerRepository.getAnswersByProfileId(profile.getId());
        List<Option> options = answers.stream().map(Answer::getOption).toList();
        options.forEach(option -> {
            List<AnswerValue> answerValuesByOptionId = answerValueRepository.getAnswerValuesByOptionId(option.getId());
            answerValuesByOptionId.forEach(answerValue -> {
                if (scoreAttrs.get(answerValue.getAttribute().getId()) != null) {
                    int newValue = answerValue.getValue() + scoreAttrs.get(answerValue.getAttribute().getId());
                    scoreAttrs.put(answerValue.getAttribute().getId(), newValue);
                } else {
                    scoreAttrs.put(answerValue.getAttribute().getId(), answerValue.getValue());
                }
            });
        });
        System.out.println(scoreAttrs);
    }
}
