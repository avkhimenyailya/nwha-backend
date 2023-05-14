package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("FROM Answer answer JOIN answer.profileTask.profile profile WHERE profile.id = :profileId")
    List<Answer> getAnswersByProfileId(@Param("profileId") Long profileId);
}
