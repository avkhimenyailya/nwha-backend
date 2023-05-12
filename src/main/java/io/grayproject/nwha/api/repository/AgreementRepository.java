package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ilya Avkhimenya
 */
@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    Optional<Agreement> findAgreementByProfileId(Long profileId);
}
