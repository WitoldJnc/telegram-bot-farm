package ru.tg.farm.common.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tg.farm.common.repository.InfoBotService;

@Component
public class InfoBotServiceImpl extends TelegramLongPollingBot implements InfoBotService {

    @Autowired
    private Environment env;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(new SendMessage(getChatId(), "init"));
        } catch (TelegramApiException e) {
            //todo log
        }
    }

    //todo обработать ошибки
    @SneakyThrows
    @Override
    public String sendInfoMessage(String message) {
        String mnemonic = String.valueOf(env.getProperty("api.bot.mnemonic"));
        String errorMessage = String.format("<b>%s</b>: %s", mnemonic, message);
        SendMessage sendMessage = new SendMessage(getChatId(), errorMessage);
        sendMessage.setParseMode("HTML");
        execute(sendMessage);
        return errorMessage;
    }

    public String getChatId() {
        return String.valueOf(env.getProperty("tg.bot.info.api.chat"));
    }

    public String getBotUsername() {
        return String.valueOf(env.getProperty("tg.bot.info.api.name"));
    }

    public String getBotToken() {
        return String.valueOf(env.getProperty("tg.bot.info.api.key"));
    }
}
