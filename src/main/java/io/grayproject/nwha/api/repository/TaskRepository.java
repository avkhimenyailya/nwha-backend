package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ilya Avkhimenya
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
