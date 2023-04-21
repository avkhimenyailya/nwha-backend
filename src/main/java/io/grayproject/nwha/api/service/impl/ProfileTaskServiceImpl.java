package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Answer;
import io.grayproject.nwha.api.domain.Option;
import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.ProfileTask;
import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import io.grayproject.nwha.api.mapper.ProfileTaskMapper;
import io.grayproject.nwha.api.repository.AnswerRepository;
import io.grayproject.nwha.api.repository.OptionRepository;
import io.grayproject.nwha.api.repository.ProfileRepository;
import io.grayproject.nwha.api.repository.ProfileTaskRepository;
import io.grayproject.nwha.api.service.ProfileTaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
@Service
public class ProfileTaskServiceImpl implements ProfileTaskService {
    private final OptionRepository optionRepository;
    private final AnswerRepository answerRepository;
    private final ProfileRepository profileRepository;
    private final ProfileTaskRepository profileTaskRepository;
    private final ProfileTaskMapper profileTaskMapper;

    @Autowired
    public ProfileTaskServiceImpl(OptionRepository optionRepository,
                                  AnswerRepository answerRepository,
                                  ProfileRepository profileRepository,
                                  ProfileTaskRepository profileTaskRepository,
                                  ProfileTaskMapper profileTaskMapper) {
        this.optionRepository = optionRepository;
        this.answerRepository = answerRepository;
        this.profileRepository = profileRepository;
        this.profileTaskRepository = profileTaskRepository;
        this.profileTaskMapper = profileTaskMapper;
    }

    @Override
    @Transactional
    public ProfileTaskDTO putAnswers(Principal principal,
                                     Long profileTaskId,
                                     List<AnswerDTO> answers) {
        ProfileTask pt = getProfileTask(principal, profileTaskId);

        List<Answer> newAnswers = answers
                .stream()
                .map(answerDTO -> {
                    Option opt = optionRepository
                            .findById(answerDTO.optionId())
                            .orElseThrow(() -> new RuntimeException("Error parsing responses"));

                    return Answer
                            .builder()
                            .profileTask(pt)
                            .option(opt)
                            .build();
                })
                .toList();

        answerRepository.saveAll(newAnswers);
        pt.setAnswers(new ArrayList<>(newAnswers));
        profileTaskRepository.save(pt);
        return profileTaskMapper.apply(pt);
    }

    @Override
    public ProfileTaskDTO removeAnswers(Principal principal,
                                        Long profileTaskId) {
        ProfileTask pt = getProfileTask(principal, profileTaskId);
        pt.setAnswers(null);
        return profileTaskMapper.apply(pt);
    }

    @Override
    public ProfileTaskDTO refreshProfileTask(Principal principal,
                                             Long profileTaskId) {
        // todo!
        return null;
    }

    private ProfileTask getProfileTask(Principal principal, Long profileTaskId) {
        Profile profile = getProfileByPrincipal(principal)
                // fatal error (this should not be)
                .orElseThrow(RuntimeException::new);

        return profile
                .getProfileTasks()
                .stream()
                .filter(profileTask -> profileTask.getId().equals(profileTaskId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Profile task with this id cannot be edited"));
    }

    private Optional<Profile> getProfileByPrincipal(Principal principal) {
        return profileRepository
                .findProfileByUserUsername(principal.getName());
    }
}
