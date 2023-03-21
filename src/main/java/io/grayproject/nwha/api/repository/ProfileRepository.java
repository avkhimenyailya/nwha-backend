package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ilya Avkhimenya
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
