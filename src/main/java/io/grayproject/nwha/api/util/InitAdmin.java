package io.grayproject.nwha.api.util;

import io.grayproject.nwha.api.domain.ERole;
import io.grayproject.nwha.api.domain.Role;
import io.grayproject.nwha.api.domain.User;
import io.grayproject.nwha.api.repository.RoleRepository;
import io.grayproject.nwha.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitAdmin {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public void init() {
        Optional<Role> roleUser = roleRepository.findRoleByName(ERole.ROLE_USER);
        Optional<Role> roleAdmin = roleRepository.findRoleByName(ERole.ROLE_ADMIN);

        String random = RandomStringUtils.random(32, true, false);
        log.warn("Be sure to keep the original code {}", random);
        User user = User
                .builder()
                .username("admin")
                .password(new BCryptPasswordEncoder(12).encode("000000"))
                .invitationCode(Base64.getEncoder().encodeToString(random.getBytes()))
                .roles(List.of(roleUser.get(), roleAdmin.get()))
                .build();

        userRepository.save(user);
    }
}
