package io.grayproject.nwha.api.controller;

import io.grayproject.nwha.api.dto.UserDTO;
import io.grayproject.nwha.api.dto.authentication.ChangePasswordDTO;
import io.grayproject.nwha.api.service.UserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Ilya Avkhimenya
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDTO getUser(Principal principal) {
        return userService.getUser(principal);
    }

    @PutMapping("/username")
    public UserDTO changeUsername(Principal principal,
                                  @RequestParam
                                  @Validated
                                  @Min(6) @Max(32)
                                  String newUsername) {
        return userService.changeUsername(principal, newUsername);
    }

    @PutMapping("/password")
    public void changePassword(Principal principal,
                               @RequestBody
                               @Validated
                               ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(principal, changePasswordDTO);
    }
}
