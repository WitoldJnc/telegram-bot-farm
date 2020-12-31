package ru.tg.farm.common.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tg.farm.common.model.KafkaEntity;
import ru.tg.farm.common.model.LogType;
import ru.tg.farm.common.repository.InfoBotSenderSerivce;

public class InfoBotSenderServiceImpl extends TelegramLongPollingBot implements InfoBotSenderSerivce {
    @Value("${tg.bot.info.api.name}")
    private String botName;

    @Value("${tg.bot.info.api.key}")
    private String botKey;

    @Value("${tg.bot.info.api.chat}")
    private String chatId;


    @Override
    public void snedToInfoBot(KafkaEntity logEntity) throws TelegramApiException {
        if (logEntity.getType().equals(LogType.ERROR)) {
            String errorMessage = String.format("<b>%s</b>: %s \n\n %s", logEntity.getSubSystem(), logEntity.getMessage(), logEntity.getCreated());
            SendMessage sendMessage = new SendMessage(chatId, errorMessage);
            sendMessage.setParseMode("HTML");
            execute(sendMessage);
        }
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        execute(new SendMessage(chatId, "init"));
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.botKey;
    }
}
