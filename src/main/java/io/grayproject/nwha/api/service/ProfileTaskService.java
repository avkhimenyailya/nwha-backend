package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
public interface ProfileTaskService {

    ProfileTaskDTO getProfileTaskById(Long id);

    List<ProfileTaskDTO> getProfileTasksByProfileId(Long profileId);

    List<ProfileTaskDTO> getProfileTasksByPrincipal(Principal principal);

    ProfileTaskDTO updateAnswers(Principal principal, Long profileTaskId, List<AnswerDTO> answers);
}
