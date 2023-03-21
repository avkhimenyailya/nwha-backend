package io.grayproject.nwha.api.controller;

import io.grayproject.nwha.api.dto.register.RegisterRequestDTO;
import io.grayproject.nwha.api.exception.BadInvitationCodeException;
import io.grayproject.nwha.api.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/register")
    private ResponseEntity<String> registerNewUser(@RequestBody RegisterRequestDTO registerRequestDTO)
            throws BadInvitationCodeException {
        return ResponseEntity.ok(registerService.register(registerRequestDTO));
    }
}

