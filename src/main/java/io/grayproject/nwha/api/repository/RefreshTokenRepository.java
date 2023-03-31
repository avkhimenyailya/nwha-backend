package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.RefreshToken;
import io.grayproject.nwha.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findRefreshTokenByUser(User user);
}
