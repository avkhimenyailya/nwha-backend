package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.dto.register.RegisterRequestDTO;
import io.grayproject.nwha.api.exception.BadInvitationCodeException;
import io.grayproject.nwha.api.service.RegisterService;
import io.grayproject.nwha.api.entity.*;
import io.grayproject.nwha.api.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Set;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final TraitRepository traitRepository;
    private final UserRepository userRepository;
    private final UserInvitationRepository userInvitationRepository;
    private final ProfileRepository profileRepository;
    private final ProfileTaskRepository profileTaskRepository;
    private final ProfileTraitRepository profileTraitRepository;

    @Override
    @Transactional
    public String register(RegisterRequestDTO registerRequestDTO) throws BadInvitationCodeException {
        // Invitation code check
          String encodeReceivedInvitingCode = encodeBase64(registerRequestDTO.invitationCode());
        User inviterUser = userRepository.getUserByInvitationCode(encodeReceivedInvitingCode)
                .orElseThrow(BadInvitationCodeException::new);

        // Create new user with encrypted password (bcrypt)
        String strongPassword = new BCryptPasswordEncoder(12).encode(registerRequestDTO.rawPassword());
        String encodeNewInvitingCode = encodeBase64(RandomStringUtils.random(32, true, false));
        Role role = roleRepository.getRoleByName("USER");
        User newUser = User.builder()
                .username(registerRequestDTO.username())
                .password(strongPassword)
                .invitationCode(encodeNewInvitingCode)
                .roles(Set.of(role))
                .build();
        userRepository.save(newUser);

        // Create new profile
        Profile newProfile = Profile.builder()
                .user(newUser)
                .build();
        profileRepository.save(newProfile);

        // Create tasks for profile
        List<Task> allTasks = taskRepository.findAll();
        List<ProfileTask> newProfileTasks = allTasks.stream()
                .map(task -> ProfileTask.builder()
                        .task(task)
                        .profile(newProfile)
                        .build())
                .toList();
        profileTaskRepository.saveAll(newProfileTasks);

        // Create traits for profile
        List<Trait> allTraits = traitRepository.findAll();
        List<ProfileTrait> newProfileTraits = allTraits.stream()
                .map(trait -> ProfileTrait.builder()
                        .trait(trait)
                        .profile(newProfile)
                        .value(0)
                        .build())
                .toList();
        profileTraitRepository.saveAll(newProfileTraits);

        // Save invitation
        UserInvitation newUserInvitation = UserInvitation.builder()
                .inviterUser(inviterUser)
                .invitedUser(newUser)
                .build();
        userInvitationRepository.save(newUserInvitation);

        return "Registration was successful";
    }

    private String encodeBase64(String string) {
        return Base64.getEncoder()
                .encodeToString(string.getBytes());
    }
}
