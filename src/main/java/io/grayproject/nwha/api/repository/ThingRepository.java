package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Thing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
public interface ThingRepository extends JpaRepository<Thing, Long> {

}
