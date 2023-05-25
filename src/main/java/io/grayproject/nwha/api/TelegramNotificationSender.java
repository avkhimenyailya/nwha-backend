package io.grayproject.nwha.api;

import io.grayproject.nwha.api.util.ChatIds;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramNotificationSender {
    private final TelegramBot bot;
    private final ChatIds chatIds;

    @SneakyThrows
    public void sendMessage(String message) {
        chatIds.chatIds.forEach(chatId -> {
            SendMessage telegramMessage = new SendMessage();
            telegramMessage.setChatId(chatId);
            telegramMessage.setText(message);
            try {
                bot.execute(telegramMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
