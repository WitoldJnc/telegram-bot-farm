package ru.tg.farm.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tg.farm.common.repository.InfoBotService;

@Component()
public class ExceptionHandler implements ru.tg.farm.common.repository.ExceptionHandler {

    @Autowired
    private InfoBotService infoBotService;

    @Override
    public String postToInfo(String cause) {
        infoBotService.sendInfoMessage(cause);
        return cause;
    }


}
