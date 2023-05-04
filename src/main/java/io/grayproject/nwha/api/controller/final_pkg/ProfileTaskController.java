package io.grayproject.nwha.api.controller.final_pkg;

import io.grayproject.nwha.api.dto.AnswerDTO;
import io.grayproject.nwha.api.dto.ProfileTaskDTO;
import io.grayproject.nwha.api.service.ProfileTaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@RestController
@RequestMapping("/pt")
@RequiredArgsConstructor
public class ProfileTaskController {
    private final ProfileTaskService profileTaskService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileTaskDTO> getProfileTaskById(@PathVariable Long id,
                                                             HttpServletRequest httpServletRequest) {
        log.info("Request to get profile task by id: {}, ip: {}",
                id,
                httpServletRequest.getHeader("X-Real-IP"));
        return ResponseEntity.ok(profileTaskService.getProfileTaskById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProfileTaskDTO> updateAnswers(Principal principal,
                                                        HttpServletRequest httpServletRequest,
                                                        @PathVariable Long id,
                                                        @RequestBody List<AnswerDTO> answers) {
        log.info("Request to update answers in profile task by id: {}, ip: {}",
                id,
                httpServletRequest.getHeader("X-Real-IP"));
        return ResponseEntity.ok(profileTaskService.updateAnswers(principal, id, answers));
    }
}
