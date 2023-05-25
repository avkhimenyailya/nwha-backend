package io.grayproject.nwha.api.repository;

import io.grayproject.nwha.api.domain.TgBotUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ilya Avkhimenya
 */
public interface TgBotUserRepository extends JpaRepository<TgBotUser, Long> {
}
