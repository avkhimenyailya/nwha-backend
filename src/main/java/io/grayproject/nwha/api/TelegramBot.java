package io.grayproject.nwha.api;

import io.grayproject.nwha.api.domain.User;
import io.grayproject.nwha.api.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.Collectors;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private UserRepository userRepository;

    public TelegramBot() {
        super("6152735742:AAGiC2eZkyrJzF4-vhSMy5Pt3m1pxYm1L6I");
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {

            String collect = userRepository
                    .findAll()
                    .stream()
                    .map(User::getUsername)
                    .collect(Collectors.joining(",\n"));

            Message message = update.getMessage();
            Long chatId = message.getChatId();

            SendMessage response = new SendMessage();
            response.setChatId(String.valueOf(chatId));
            response.setText(collect);
            execute(response);
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
