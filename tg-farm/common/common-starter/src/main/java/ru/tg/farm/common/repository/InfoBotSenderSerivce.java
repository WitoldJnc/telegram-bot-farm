package ru.tg.farm.common.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.tg.farm.common.model.KafkaEntity;

public interface InfoBotSenderSerivce {
    void listenTopic(KafkaEntity message) throws JsonProcessingException;
}
