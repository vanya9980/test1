package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.lang.System.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@NoArgsConstructor
public class testBot extends TelegramLongPollingBot {
    private static final Logger log = System.getLogger(testBot.class.getName());

    final int RECONNECT_PAUSE =10000;

    @Setter
    @Getter
    String botName;

    @Setter
    String botToken;

    public testBot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.log(Logger.Level.DEBUG, "Receive new Update. updateID: " + update.getUpdateId());

        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();

        if (inputText.startsWith("/start")) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Hello. This is start message");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        log.log(Logger.Level.DEBUG,"Bot name: " + botName);
        return botName;
    }

    @Override
    public String getBotToken() {
        log.log(Logger.Level.DEBUG,"Bot token: " + botToken);
        return botToken;
    }

    public void botConnect() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(this);
            log.log(Logger.Level.INFO, "TelegramAPI started. Look for messages");
        } catch (TelegramApiException e) {
            log.log(Logger.Level.ERROR, "Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}