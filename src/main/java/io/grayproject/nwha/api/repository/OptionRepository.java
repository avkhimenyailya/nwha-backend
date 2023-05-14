package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
public interface OptionRepository extends JpaRepository<Option, Long> {

    Optional<Option> findByDescription(String description);
}
