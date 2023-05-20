package io.grayproject.nwha.api;

import io.grayproject.nwha.api.domain.User;
import io.grayproject.nwha.api.repository.UserRepository;
import io.grayproject.nwha.api.util.ChatIds;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final ChatIds chatIds;
    private final UserRepository userRepository;

    @Autowired
    public TelegramBot(ChatIds chatIds, UserRepository userRepository) {
        super("6152735742:AAGiC2eZkyrJzF4-vhSMy5Pt3m1pxYm1L6I");
        this.userRepository = userRepository;
        this.chatIds = chatIds;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().getText().equals("get all users")) {
                String allUsernames = userRepository
                        .findAll()
                        .stream()
                        .map(User::getUsername)
                        .collect(Collectors.joining(",\n"));
                Message message = update.getMessage();
                Long chatId = message.getChatId();
                SendMessage response = new SendMessage();
                response.setChatId(String.valueOf(chatId));
                response.setText(allUsernames);
                execute(response);
            }
            if (update.getMessage().getText().equals("subscribe updates")) {
                Message message = update.getMessage();
                Long chatId = message.getChatId();

                SendMessage response = new SendMessage();
                response.setChatId(String.valueOf(chatId));
                if (chatIds.chatIds.contains(chatId)) {
                    response.setText("you are already subscribed to updates, it is impossible to unsubscribe");
                } else {
                    chatIds.chatIds.add(chatId);
                    response.setText("successful subscription to nwha updates");
                }
                execute(response);
            }
        }
    }

    @PostConstruct
    public void start() {
        log.info("username: {}, token: {}", getBotUsername(), getBotToken());
    }

    @Override
    public String getBotUsername() {
        return "nwhabot";
    }
}
