package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.*;
import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import io.grayproject.nwha.api.exception.EntityNotFoundException;
import io.grayproject.nwha.api.mapper.ProfileTaskMapper;
import io.grayproject.nwha.api.repository.*;
import io.grayproject.nwha.api.service.ProfileService;
import io.grayproject.nwha.api.service.ProfileTaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private final ProfileTraitRepository profileTraitRepository;

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
        Map<Long, Integer> scores = new TreeMap<>(Comparator.comparing(Long::longValue));
        List<Answer> answers = answerRepository.getAnswersByProfileId(profile.getId());
        List<Option> options = answers.stream().map(Answer::getOption).toList();
        options.forEach(option -> {
            List<AnswerValue> answerValuesByOptionId = answerValueRepository.getAnswerValuesByOptionId(option.getId());
            answerValuesByOptionId.forEach(answerValue -> {
                if (scores.get(answerValue.getAttribute().getId()) != null) {
                    int newValue = answerValue.getValue() + scores.get(answerValue.getAttribute().getId());
                    scores.put(answerValue.getAttribute().getId(), newValue);
                } else {
                    scores.put(answerValue.getAttribute().getId(), answerValue.getValue());
                }
            });
        });
        for (long i = 1; i <= 10; i++) {
            scores.putIfAbsent(i, 0);
        }

        // Conductor — Producer
        attributePairCalc(profile, 1L, 2L, scores);

        // Altruistic — Separate
        attributePairCalc(profile, 3L, 4L, scores);

        // Extravert — Introvert
        attributePairCalc(profile, 5L, 6L, scores);

        // Order — Disorder
        attributePairCalc(profile, 7L, 8L, scores);

        // Mind — Feeling
        attributePairCalc(profile, 9L, 10L, scores);
    }

    private void attributePairCalc(Profile profile,
                                   Long id1, Long id2,
                                   Map<Long, Integer> scores) {
        Integer attrVal1 = scores.get(id1);
        Integer attrVal2 = scores.get(id2);

        double coefficient = (double) 100 / (attrVal1 + attrVal2);
        double profileAttrVal1 = attrVal1 * coefficient;
        double profileAttrVal2 = attrVal2 * coefficient;

        ProfileAttribute profileAttribute1 = profile.getProfileAttributes()
                .stream()
                .filter(profileAttribute -> profileAttribute.getAttribute().getId().equals(id1))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        ProfileAttribute profileAttribute2 = profile.getProfileAttributes()
                .stream()
                .filter(profileAttribute -> profileAttribute.getAttribute().getId().equals(id2))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        profileAttribute1.setValue((int) Math.round(profileAttrVal1));
        profileAttribute2.setValue((int) Math.round(profileAttrVal2));

        profileTraitRepository.save(profileAttribute1);
        profileTraitRepository.save(profileAttribute2);
    }

}
