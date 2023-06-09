package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findProfileByUserUsername(String username);
}
