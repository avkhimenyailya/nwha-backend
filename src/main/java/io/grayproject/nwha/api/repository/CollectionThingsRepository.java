package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.CollectionThings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
public interface CollectionThingsRepository extends JpaRepository<CollectionThings, Long> {

    List<CollectionThings> findAllByProfileId(Long profileId);
}
