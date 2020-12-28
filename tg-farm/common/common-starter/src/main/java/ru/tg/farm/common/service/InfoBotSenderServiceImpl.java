package ru.tg.farm.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.tg.farm.common.model.KafkaEntity;
import ru.tg.farm.common.repository.InfoBotSenderSerivce;

@Component
@Slf4j
public class InfoBotSenderServiceImpl implements InfoBotSenderSerivce {

    @Override
    @KafkaListener(topics = "${kafka.log.topic}", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "KafkaEntityKafkaListenerContainerFactory")
    public void listenTopic(KafkaEntity message) {
        //todo logic post to bot
    }

}
