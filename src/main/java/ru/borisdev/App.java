package ru.borisdev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.borisdev.telegram.TranslateFileResourcesBot;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TranslateFileResourcesBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}