package ru.dailypron.bot.service;

import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler implements ru.dailypron.bot.repo.ExceptionHandler {

    @Override
    public void postToInfo(String cause) {
        System.out.println(cause);
    }
}
