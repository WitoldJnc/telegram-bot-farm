package ru.tg.farm.common.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.tg.farm.common.model.KafkaEntity;
import ru.tg.farm.common.repository.InfoBotSenderSerivce;

@Component
public class InfoBotSenderServiceImpl implements InfoBotSenderSerivce {


    @KafkaListener(topics = "${kafka.log.topic}")
    void listener(String message) {
        int a = 3;
    }


}
