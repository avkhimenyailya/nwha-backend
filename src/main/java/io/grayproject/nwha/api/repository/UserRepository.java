package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Optional<User> getUserByInvitationCode(String invitationCode);
}
