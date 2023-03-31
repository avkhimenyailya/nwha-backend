package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.UserInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ilya Avkhimenya
 */
public interface UserInvitationRepository extends JpaRepository<UserInvitation, Long> {
}
