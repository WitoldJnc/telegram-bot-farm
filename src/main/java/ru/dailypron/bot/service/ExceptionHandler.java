package ru.dailypron.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dailypron.bot.repo.info.InfoBotService;

@Component
public class ExceptionHandler implements ru.dailypron.bot.repo.ExceptionHandler {

    @Autowired
    private InfoBotService infoBotService;

    @Override
    public String postToInfo(String cause) {
        infoBotService.sendInfoMessage(cause);
        return cause;
    }


}
