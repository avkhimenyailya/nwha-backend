package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import io.grayproject.nwha.api.service.ProfileTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/pt")
public class ProfileTaskController {
    private final ProfileTaskService profileTaskService;

    @Autowired
    public ProfileTaskController(ProfileTaskService profileTaskService) {
        this.profileTaskService = profileTaskService;
    }

    @GetMapping("/{id}")
    public ProfileTaskDTO getProfileTaskById(@PathVariable Long id) {
        return profileTaskService.getProfileTaskById(id);
    }

    @PutMapping("{profileTaskId}/put/answer")
    public ProfileTaskDTO putAnswers(Principal principal,
                                     @PathVariable Long profileTaskId,
                                     @RequestBody List<AnswerDTO> answers) {
        return profileTaskService.putAnswers(principal, profileTaskId, answers);
    }

    @PutMapping("{profileTaskId}/rm/answer")
    public ProfileTaskDTO removeAnswers(Principal principal,
                                        @PathVariable Long profileTaskId) {
        return profileTaskService.removeAnswers(principal, profileTaskId);
    }

    @PutMapping("{profileTaskId}/refresh")
    public ProfileTaskDTO refreshProfileTask(Principal principal,
                                             @PathVariable Long profileTaskId) {
        return profileTaskService.refreshProfileTask(principal, profileTaskId);
    }
}
