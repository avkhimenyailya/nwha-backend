package io.grayproject.nwha.api;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author Ilya Avkhimenya
 */
@Component
@RequiredArgsConstructor
public class TelegramNotificationSender {
    private final TelegramBot bot;

    @SneakyThrows
    public void sendMessage(String message) {
        SendMessage telegramMessage = new SendMessage();
        telegramMessage.setChatId("5006845421");
        telegramMessage.setText(message);
        bot.execute(telegramMessage);
    }
}
