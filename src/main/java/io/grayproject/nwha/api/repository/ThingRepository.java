package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Thing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Ilya Avkhimenya
 */
@Repository
public interface ThingRepository extends JpaRepository<Thing, Long> {

    @Query("SELECT COUNT(DISTINCT c.id) FROM CollectionThings c JOIN c.things i WHERE i.id = :thingId")
    Integer amountCollectionsByThingId(@Param("thingId") Long thingId);
}
