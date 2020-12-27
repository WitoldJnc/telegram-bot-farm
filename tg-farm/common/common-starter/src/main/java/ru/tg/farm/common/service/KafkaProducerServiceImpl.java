package ru.tg.farm.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tg.farm.common.model.KafkaEntity;
import ru.tg.farm.common.repository.KafkaProducerService;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Value("${spring.kafka.producer.topic}")
    String kafkaTopic;

    @Autowired
    private KafkaTemplate<String, KafkaEntity> template;

    @Override
    public void sendMessage(KafkaEntity logEntity) {

        template.send(kafkaTopic, logEntity);
    }

}
