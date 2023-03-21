package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.entity.Trait;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ilya Avkhimenya
 */
public interface TraitRepository extends JpaRepository<Trait, Long> {
}
