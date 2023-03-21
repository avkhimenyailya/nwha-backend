package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ilya Avkhimenya
 */
public interface OptionRepository extends JpaRepository<Option, Long> {
}
