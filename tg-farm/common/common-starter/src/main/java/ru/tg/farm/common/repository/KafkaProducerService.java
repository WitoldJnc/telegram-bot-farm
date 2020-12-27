package ru.tg.farm.common.repository;

import ru.tg.farm.common.model.KafkaEntity;

public interface KafkaProducerService {
    void sendMessage(KafkaEntity logEntity);
}
