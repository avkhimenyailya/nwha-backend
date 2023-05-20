package io.grayproject.nwha.api.service.impl;

import io.grayproject.nwha.api.TelegramNotificationSender;
import io.grayproject.nwha.api.domain.*;
import io.grayproject.nwha.api.dto.authentication.LoginRequest;
import io.grayproject.nwha.api.dto.authentication.RegisterRequest;
import io.grayproject.nwha.api.exception.BadInvitationCodeException;
import io.grayproject.nwha.api.repository.*;
import io.grayproject.nwha.api.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final TraitRepository traitRepository;
    private final UserRepository userRepository;
    private final UserInvitationRepository userInvitationRepository;
    private final ProfileRepository profileRepository;
    private final ProfileTaskRepository profileTaskRepository;
    private final ProfileTraitRepository profileTraitRepository;
    private final AgreementRepository agreementRepository;
    private final TelegramNotificationSender telegramNotificationSender;

    @Override
    @Transactional
    public LoginRequest register(RegisterRequest registerRequest) {
        // Invitation code check
        String encodeReceivedInvitingCode = encodeBase64(registerRequest.invitationCode());
        User inviterUser = userRepository.getUserByInvitationCode(encodeReceivedInvitingCode)
                .orElseThrow(() -> new BadInvitationCodeException(registerRequest.invitationCode()));

        // Create new user with encrypted password
        String strongPassword = passwordEncoder.encode(registerRequest.password());
        String encodeNewInvitingCode = encodeBase64(RandomStringUtils.random(12, true, true));

        Optional<Role> roleByName = roleRepository.findRoleByName(ERole.ROLE_USER);
        User newUser = User.builder()
                .username(registerRequest.username().trim().toLowerCase(Locale.ROOT))
                .password(strongPassword)
                .invitationCode(encodeNewInvitingCode)
                .roles(List.of(roleByName.get()))
                .build();
        try {
            userRepository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException(String.format("User %s already exists", registerRequest.username().trim().toLowerCase()));
        }

        // Create new profile
        Profile newProfile = Profile.builder()
                .user(newUser)
                .removed(false)
                .build();
        profileRepository.save(newProfile);

        // Create agreements
        Agreement agreement = new Agreement();
        agreement.setProfile(newProfile);
        agreement.setAcceptedPhotoUsage(true);
        agreementRepository.save(agreement);

        // Create tasks for profile
        List<Task> allTasks = taskRepository.findAll();
        List<ProfileTask> newProfileTasks = allTasks.stream()
                .map(task -> ProfileTask.builder()
                        .task(task)
                        .profile(newProfile)
                        .removed(false)
                        .build())
                .toList();
        profileTaskRepository.saveAll(newProfileTasks);

        // Create traits for profile
        AtomicInteger val = new AtomicInteger();
        AtomicInteger counter = new AtomicInteger(1);
        List<Attribute> allAttributes = traitRepository.findAll();
        List<ProfileAttribute> newProfileAttributes = allAttributes.stream()
                .map(trait -> {
                    if (counter.getAndIncrement() == 1) {
                        val.set((int) (1 + Math.random() * 100));
                    } else {
                        val.set(100 - val.get());
                        counter.set(1);
                    }
                    return ProfileAttribute.builder()
                            .attribute(trait)
                            .profile(newProfile)
                            .value(val.get())
                            .build();
                })
                .toList();
        profileTraitRepository.saveAll(newProfileAttributes);

        // Save invitation
        UserInvitation newUserInvitation = UserInvitation.builder()
                .inviterUser(inviterUser)
                .invitedUser(newUser)
                .build();
        userInvitationRepository.save(newUserInvitation);

        telegramNotificationSender.sendMessage("+ new user " + newUser.getUsername());
        // передаем результат регистрации в логин-сервис,
        // чтобы сразу получить токены для авторизации
        return LoginRequest
                .builder()
                .username(newProfile.getUser().getUsername())
                .password(registerRequest.password())
                .build();
    }

    private String encodeBase64(String string) {
        return Base64.getEncoder()
                .encodeToString(string.getBytes());
    }
}
