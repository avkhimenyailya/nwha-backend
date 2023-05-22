package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.RandomThingOfDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
@Repository
public interface RandomThingOfDayRepository extends JpaRepository<RandomThingOfDay, Long> {

    @Query("FROM RandomThingOfDay r WHERE DATE(r.createdAt) = CURRENT_DATE() AND r.thing.removed = false AND r.thing.archived = false")
    Optional<RandomThingOfDay> findRandomThingOfDayToday();
}
