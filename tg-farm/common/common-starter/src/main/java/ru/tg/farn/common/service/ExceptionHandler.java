package ru.tg.farn.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tg.farn.common.repository.InfoBotService;

@Component
public class ExceptionHandler implements ru.tg.farn.common.repository.ExceptionHandler {

    @Autowired
    private InfoBotService infoBotService;

    @Override
    public String postToInfo(String cause) {
        infoBotService.sendInfoMessage(cause);
        return cause;
    }


}
