package org.example;

import java.lang.System.Logger;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    private static final Logger log = System.getLogger(Main.class.getName());
    public static void main(String[] args) throws TelegramApiException {
        testBot myTestBot = new testBot("s1lent_JavaProject_Testbot", "7138847779:AAHvBvcxW1CxC8CFGNCNeX9nXbtnX2Jq_Mo");
        myTestBot.botConnect();
    }
}