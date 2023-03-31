package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Trait;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ilya Avkhimenya
 */
public interface TraitRepository extends JpaRepository<Trait, Long> {
}
