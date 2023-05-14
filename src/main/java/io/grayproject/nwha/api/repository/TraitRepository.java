package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
public interface TraitRepository extends JpaRepository<Attribute, Long> {

    Optional<Attribute> findAttributeByName(String name);
}
