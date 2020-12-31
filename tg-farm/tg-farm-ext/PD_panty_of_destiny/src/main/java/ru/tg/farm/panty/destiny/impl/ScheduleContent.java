package ru.tg.farm.panty.destiny.impl;

import lombok.extern.slf4j.Slf4j;
import ru.tg.farm.panty.destiny.service.ContentSender;
import ru.tg.farm.panty.destiny.service.ScheduleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static ru.tg.farm.panty.destiny.model.ScheduleStatuses.*;

@Component
@EnableScheduling
@PropertySource("application.yml")
@Slf4j
public class ScheduleContent {
    @Autowired
    private ContentSender contentSender;
    @Autowired
    private ScheduleStatusService scheduleStatus;

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 11 * * ?")
    private void sendManContentAt11() {
        if(scheduleStatus.getScheduleStatusByCode(MAN11.name()).getStatus()) {
            contentSender.sendManContent();
            log.info("man content at 11 is passing");
        }
    }

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 15 * * ?")
    private void sendWomanContentAt15() {
        if(scheduleStatus.getScheduleStatusByCode(WOMAN15.name()).getStatus()){
            contentSender.sendWomanContent();
            log.info("woman content at 15 is passing");
        }
    }

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 17 * * ?")
    private void sendManWeirdAt17() {
        if(scheduleStatus.getScheduleStatusByCode(MANS17.name()).getStatus()){
            contentSender.sendManWeird();
            log.info("man weird content at 17 is passing");
        }
    }

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 21 * * ?")
    private void sendRandomContentAt21() {
        if(scheduleStatus.getScheduleStatusByCode(RANDOM21.name()).getStatus()) {
            contentSender.sendRandomContent();
            log.info("man random content at 21 is passing");
        }
    }

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 23 * * ?")
    private void sendWomanContentOf23() {
        if(scheduleStatus.getScheduleStatusByCode(WOMANS23.name()).getStatus()){
            contentSender.sendWomanWeird();
            log.info("woman weird content at 23 is passing");
        }
    }
}
