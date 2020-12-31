package ru.tg.farm.panty.destiny.controller;

import ru.tg.farm.common.exception.ApiExcetionNeedToLog;
import ru.tg.farm.panty.destiny.model.ScheduleStatus;
import ru.tg.farm.panty.destiny.service.ContentSender;
import ru.tg.farm.panty.destiny.service.InfoService;
import ru.tg.farm.panty.destiny.service.ScheduleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SendController {
    @Autowired
    private Environment env;

    @Autowired
    private ContentSender scheduledContent;

    @Autowired
    private InfoService infoService;

    @Autowired
    private ScheduleStatusService scheduleStatus;

    @GetMapping
    public String mainInfo() {
        return infoService.getInfo();
    }

    @GetMapping("/random/{key}")
    public void sendRandom(@PathVariable("key") String key) throws ApiExcetionNeedToLog {
        if (!env.getProperty("pod.tg.api.bot.secure").equals(key)) {
            return;
        }
        scheduledContent.sendRandomContent();
    }

    @GetMapping("/man/{key}")
    public void sendMan(@PathVariable("key") String key) throws ApiExcetionNeedToLog {
        if (!env.getProperty("pod.tg.api.bot.secure").equals(key)) {
            return;
        }
        scheduledContent.sendManContent();
    }

    @GetMapping("/woman/{key}")
    public void sendWoman(@PathVariable("key") String key) throws ApiExcetionNeedToLog {
        if (!env.getProperty("pod.tg.api.bot.secure").equals(key)) {
            return;
        }
        scheduledContent.sendWomanContent();
    }

    @GetMapping("/womans/{key}")
    public void sendWomanShiza(@PathVariable("key") String key) throws ApiExcetionNeedToLog {
        if (!env.getProperty("pod.tg.api.bot.secure").equals(key)) {
            return;
        }
        scheduledContent.sendWomanWeird();
    }

    @GetMapping("/mans/{key}")
    public void sendManShiza(@PathVariable("key") String key) throws ApiExcetionNeedToLog {
        if (!env.getProperty("pod.tg.api.bot.secure").equals(key)) {
            return;
        }
        scheduledContent.sendManWeird();
    }

    //todo after tests replace to post
    @GetMapping("/{code}")
    public void updateStatus(@PathVariable("code") String code) {
        ScheduleStatus scheduleStatusByCode = scheduleStatus.getScheduleStatusByCode(code);
        scheduleStatusByCode.setStatus(!scheduleStatusByCode.getStatus());
        scheduleStatus.save(scheduleStatusByCode);
    }
}
