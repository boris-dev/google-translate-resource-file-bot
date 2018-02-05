package ru.borisdev.telegram;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.borisdev.service.SongTranslator;

import java.io.FileReader;
import java.io.InputStream;

public class TranslateFileResourcesBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasDocument()) {
            Document document = update.getMessage().getDocument();

            GetFile getFile = new GetFile();
            getFile.setFileId(document.getFileId());

            try {
                File execute = execute(getFile);
                java.io.File file = downloadFile(execute);
                String content = IOUtils.toString(new FileReader(file));
                InputStream outputFileInputStream = null;
                if (document.getFileName().endsWith(".json")) {
                    outputFileInputStream = translateVocaberryJson(content);
                } else if (document.getFileName().toLowerCase().contains("string.xml")) {
                    outputFileInputStream = translateAndroidStrings(content);
                }
                if (outputFileInputStream != null) {
                    SendDocument sendDocument = new SendDocument()
                            .setChatId(update.getMessage().getChatId())
                            .setCaption(document.getFileName() + " with translations")
                            .setNewDocument(document.getFileName(), outputFileInputStream);
                    sendNoException(sendDocument);
                } else {
                    SendMessage message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText(document.getFileName() + " is not supported");
                    sendNoException(message);
                }
            } catch (Throwable e) {
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(e.toString());
                sendNoException(message);
            }
        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {

                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText("Service doesn't work with text, only .json files vocaberry format and android string.xml in English");

                sendNoException(message);
            } catch (Throwable e) {
                SendMessage message = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(e.toString());
                sendNoException(message);
            }
        }
    }

    private InputStream translateAndroidStrings(String content) {
        return null;
    }

    @NotNull
    private InputStream translateVocaberryJson(String content) {
        SongTranslator songTranslator = new SongTranslator(content);
        String translate = songTranslator.translate();
        return IOUtils.toInputStream(translate);
    }


    private void sendNoException(BotApiMethod message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendNoException(SendDocument message) {
        try {
            sendDocument(message);
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
