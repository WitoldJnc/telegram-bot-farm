package ru.tg.farm.common.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tg.farm.common.model.KafkaEntity;
import ru.tg.farm.common.model.LogType;
import ru.tg.farm.common.repository.KafkaProducerService;
import ru.tg.farm.common.repository.LogService;

import java.time.LocalDateTime;

@Component
@Slf4j
public class LogServiceImpl implements LogService {

    @Value("${tg.bot.mnemonic}")
    private String mnemonic;

    @Value("${spring.kafka.producer.id}")
    private String elkIndex;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Override
    public void logToKafka(String message) {
        KafkaEntity logEntity = initKafkaEntity(message);
        log(logEntity);
        kafkaProducerService.sendMessage(logEntity);
    }

    private void log(KafkaEntity kafkaEntity){
        if(kafkaEntity.getType().equals(LogType.ERROR)){
            log.error("tg-farm-exception: " + kafkaEntity);
        } else {
            log.info("tg-farm-passing: " + kafkaEntity);
        }
    }

    private KafkaEntity initKafkaEntity(String message) {
        boolean isException = StringUtils.containsIgnoreCase(message, "exception") ||
                StringUtils.containsIgnoreCase(message, "runtime");
        return KafkaEntity.builder()
                .created(LocalDateTime.now())
                .subSystem(mnemonic)
                .elkIndex(elkIndex)
                .type(!isException ? LogType.INFO : LogType.ERROR)
                .message(message)
                .build();
    }
}
