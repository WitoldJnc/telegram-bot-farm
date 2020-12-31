package ru.tg.farm.panty.destiny.service;

import ru.tg.farm.common.exception.ApiExcetionNeedToLog;

public interface ContentSender {
    void sendManContent() throws ApiExcetionNeedToLog;

    void sendWomanContent() throws ApiExcetionNeedToLog;

    void sendManWeird() throws ApiExcetionNeedToLog;

    void sendWomanWeird() throws ApiExcetionNeedToLog;

    void sendRandomContent() throws ApiExcetionNeedToLog;
}
