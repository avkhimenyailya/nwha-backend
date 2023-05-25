package io.grayproject.nwha.api.util;

import io.grayproject.nwha.api.domain.TgBotUser;
import io.grayproject.nwha.api.repository.TgBotUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ilya Avkhimenya
 */
@Component
public class ChatIds {
    public final Set<Long> chatIds;
    public final TgBotUserRepository tgBotUserRepository;

    @Autowired
    public ChatIds(TgBotUserRepository tgBotUserRepository) {
        this.tgBotUserRepository = tgBotUserRepository;
        chatIds = tgBotUserRepository
                .findAll()
                .stream()
                .map(TgBotUser::getChatId)
                .collect(Collectors.toSet());
    }
}
