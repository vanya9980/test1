package org.example.dbo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static final String FILE_NAME = "src\\main\\java\\org.example\\dbo\\db\\chat_data.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Map<Long, ChatData> chatDataMap = new HashMap<>();

    //внутрішній клас, свого роду нода, тип даних для позначення того, що до мапи з ключем chatId належить об’єкт ChatData, що містить інфу про назву банку, к-сть знаків після коми та валюти, курс яких буде показуватись.
    private static class ChatData {
        String currency;  /* На вибір, хто як захоче(валюта як цифра чи валюта як слово)
        повинно бути передано до сеттеру у вигляді однієї строки, записано через кому,
        далі повинна йти перевірка на те які валюти вибрані для того щоб змінювати кнопки. */
        int decimalPlaces;  //0-3
        int bankName; //1 - НБУ, 2 - ПриватБанк, 3 - МоноБанк

        ChatData(String currency, int decimalPlaces, int bankName) {
            this.currency = currency;
            this.decimalPlaces = decimalPlaces;
            this.bankName = bankName;
        }
    }

    public User() {
        //Метод LoadFromFile, який отримує інфу з файлу json та дозволяє змінювати інфу надалі.
        loadFromFile();
    }
    //запис інфи про назву валюти та подальше її збереження до файлу json
    public void setCurrency(long chatId, String currency) {
        getOrCreateChatData(chatId).currency = currency;
        saveToFile();
    }
    //запис інфи про к-сть знаків після коми та подальше її збереження до файлу json
    public void setDecimalPlaces(long chatId, int decimalPlaces) {
        getOrCreateChatData(chatId).decimalPlaces = decimalPlaces;
        saveToFile();
    }
    //запис інфи про назву банку та подальше її збереження до файлу json
    public void setBankName(long chatId, int bankName) {
        getOrCreateChatData(chatId).bankName = bankName;
        saveToFile();
    }

    //створення нової комірки ChatData, якщо користувач новий/відсутній у файлі, інакше - повернути дану комірку.
    private ChatData getOrCreateChatData(long chatId) {
        return chatDataMap.computeIfAbsent(chatId, k -> new ChatData("", 0, 0));
    }
    //Отримання мапи(інфи з ДБ) для подальшої її зміни та запису.
    private void loadFromFile() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type type = new TypeToken<Map<Long, ChatData>>(){}.getType();
            chatDataMap = gson.fromJson(reader, type);
            if (chatDataMap == null) {
                chatDataMap = new HashMap<>();
            }
        } catch (IOException e) {
            chatDataMap = new HashMap<>();
        }
    }
    //Перезапис мапи до файлу json
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(chatDataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
