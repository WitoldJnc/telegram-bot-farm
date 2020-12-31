package ru.tg.farm.common.exception;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tg.farm.common.repository.LogService;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *  Аспект для отлова исключений и логирования в кафку(для логов в елк), чтобы позже вычитать их из топика и обработать
 */

@Component
@Aspect
public class ExceptionHandlerAspect {

    @Autowired
    private LogService logService;

    @AfterThrowing(pointcut = "execution(* ru.tg.farm..* (..))", throwing = "ex")
    public void errorInterceptor(ApiExcetionNeedToLog ex) {
        String stackTraceAsString = getStackTraceAsString(ex);
        logService.logToKafka(stackTraceAsString);
    }

    private String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
