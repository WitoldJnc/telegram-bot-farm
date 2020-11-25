package ru.dailypron.bot.repo;

import ru.dailypron.bot.model.DailyEntity;

import java.util.Optional;

public interface CustomDailyEntityService {
    Optional<DailyEntity> changeEntityStatus(DailyEntity entity);

    Optional<DailyEntity> findRandom();

    Optional<DailyEntity> findAny();

    Iterable<DailyEntity> deleteAllEntitiesByStatusIsTrue();

    Iterable<DailyEntity> createNewRecords();
}
