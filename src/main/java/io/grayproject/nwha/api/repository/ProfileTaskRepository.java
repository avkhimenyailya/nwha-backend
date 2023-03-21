package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.entity.ProfileTask;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ilya Avkhimenya
 */
public interface ProfileTaskRepository extends JpaRepository<ProfileTask, Long> {
}
