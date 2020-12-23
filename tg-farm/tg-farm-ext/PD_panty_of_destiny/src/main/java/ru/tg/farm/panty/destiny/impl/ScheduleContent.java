package ru.tg.farm.panty.destiny.impl;

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
public class ScheduleContent {
    @Autowired
    private ContentSender contentSender;
    @Autowired
    private ScheduleStatusService scheduleStatus;

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 11 * * ?")
    private void sendManConentAt11() {
        if(scheduleStatus.getScheduleStatusByCode(MAN11.name()).getStatus()) {
            contentSender.sendManContent();
        }
    }

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 15 * * ?")
    private void sendWomanConentAt15() {
        if(scheduleStatus.getScheduleStatusByCode(WOMAN15.name()).getStatus()){
            contentSender.sendWomanContent();
        }
    }

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 17 * * ?")
    private void sendManShizaAt17() {
        if(scheduleStatus.getScheduleStatusByCode(MANS17.name()).getStatus()){
            contentSender.sendManShiza();
        }
    }

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 21 * * ?")
    private void sendRandomConentAt21() {
        if(scheduleStatus.getScheduleStatusByCode(RANDOM21.name()).getStatus()) {
            contentSender.sendRandomContent();
        }
    }

    @Scheduled(cron = "0 #{new java.util.Random().nextInt(30)} 23 * * ?")
    private void sendWomanConentOf23() {
        if(scheduleStatus.getScheduleStatusByCode(WOMANS23.name()).getStatus()){
            contentSender.sendWomanShiza();
        }
    }
}
