package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.AnswerValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerValueRepository extends JpaRepository<AnswerValue, Long> {

    List<AnswerValue> getAnswerValuesByOptionId(Long optionId);
}
