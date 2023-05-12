package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.domain.Profile;
import io.grayproject.nwha.api.domain.User;
import io.grayproject.nwha.api.dto.UserDTO;
import io.grayproject.nwha.api.dto.authentication.ChangePasswordDTO;
import io.grayproject.nwha.api.repository.UserRepository;
import io.grayproject.nwha.api.service.ProfileService;
import io.grayproject.nwha.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUser(Principal principal) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);
        User user = profile.getUser();
        return UserDTO
                .builder()
                .username(user.getUsername())
                .invitationCode(new String(Base64.getDecoder().decode(user.getInvitationCode())))
                .build();
    }

    @Override
    public UserDTO changeUsername(Principal principal, String username) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);
        User user = profile.getUser();
        user.setUsername(username);
        User saved = userRepository.save(user);
        return UserDTO
                .builder()
                .username(saved.getUsername())
                .invitationCode(new String(Base64.getDecoder().decode(saved.getInvitationCode())))
                .build();
    }

    @Override
    public void changePassword(Principal principal, ChangePasswordDTO changePasswordDTO) {
        Profile profile = profileService.getProfileEntityByPrincipal(principal);
        User user = profile.getUser();

        boolean oldPasswordValid
                = passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword());
        if (!oldPasswordValid) {
            throw new RuntimeException("Old password is not correct");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }
}
