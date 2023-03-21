package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ilya Avkhimenya
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
