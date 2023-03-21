package io.grayproject.nwha.api.util;

import io.grayproject.nwha.api.entity.Role;
import io.grayproject.nwha.api.entity.User;
import io.grayproject.nwha.api.repository.RoleRepository;
import io.grayproject.nwha.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitAdmin {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public void init() {
        Role roleUser = roleRepository.getRoleById(1L);
        Role roleAdmin = roleRepository.getRoleById(3L);

        String random = RandomStringUtils.random(32, true, false);
        log.warn("Be sure to keep the original code {}", random);
        User user = User
                .builder()
                .username("admin")
                .password(new BCryptPasswordEncoder(12).encode("000000"))
                .invitationCode(Base64.getEncoder().encodeToString(random.getBytes()))
                .roles(Set.of(roleUser, roleAdmin))
                .build();

        userRepository.save(user);
    }
}
