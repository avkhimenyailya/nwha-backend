package io.grayproject.nwha.api.service;

import io.grayproject.nwha.api.domain.ProfileTask;
import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
public interface ProfileTaskService {

    ProfileTaskDTO getProfileTaskById(Long id);

    ProfileTaskDTO putAnswers(Principal principal,
                              Long profileTaskId,
                              List<AnswerDTO> answers);

    ProfileTaskDTO removeAnswers(Principal principal,
                                 Long profileTaskId);

    ProfileTaskDTO refreshProfileTask(Principal principal,
                                      Long profileTaskId);
}
