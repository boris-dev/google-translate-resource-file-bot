package ru.borisdev.telegram;

import org.apache.commons.io.IOUtils;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.borisdev.service.SongTranslator;

public class TranslateFileResourcesBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasDocument()) {
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText("FILE");

            sendNoException(message);
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                SongTranslator songTranslator = new SongTranslator(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("sample.json")));
                String translate = songTranslator.translate();


                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(translate.substring(0, 500));

                sendNoException(message);
            } catch (Throwable e) {
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(e.toString());
                sendNoException(message);
            }
        }
    }


    private void sendNoException(BotApiMethod message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return "translate-file-resources-bot";
    }

    @Override
    public String getBotToken() {
        return "451070756:AAF_QncyHxXzME1lWjKDpHTRX75BWdG6O1k";
    }
}
