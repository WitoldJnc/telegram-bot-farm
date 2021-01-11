package ru.tg.farm.common.repository;

public interface LogService {
    void logToKafka(String message);
}
