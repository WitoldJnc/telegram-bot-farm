package ru.tg.farm.common.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tg.farm.common.model.KafkaEntity;

public interface InfoBotSenderSerivce {

    void snedToInfoBot(KafkaEntity logEntity) throws TelegramApiException;
}
