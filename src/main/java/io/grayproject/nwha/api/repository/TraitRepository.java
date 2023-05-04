package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ilya Avkhimenya
 */
public interface TraitRepository extends JpaRepository<Attribute, Long> {
}
