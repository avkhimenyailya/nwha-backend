package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.CollectionThings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
public interface CollectionThingsRepository extends JpaRepository<CollectionThings, Long> {

    @Query("SELECT COUNT(DISTINCT c.id) FROM CollectionThings c JOIN c.things i WHERE i.id = :thingId")
    Integer countCollectionsByThingId(@Param("itemId") Long thingId);

    List<CollectionThings> findAllByProfileId(Long profileId);
}
