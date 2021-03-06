package ru.tg.farm.daily.trash.repo;


import ru.tg.farm.common.exception.ApiExcetionNeedToLog;
import ru.tg.farm.daily.trash.model.DailyEntity;

import java.util.Optional;

public interface CustomDailyEntityService {
    Optional<DailyEntity> changeEntityStatus(DailyEntity entity);

    Optional<DailyEntity> findRandom();

    Optional<DailyEntity> findAny();

    Iterable<DailyEntity> deleteAllEntitiesByStatusIsTrue();

    Iterable<DailyEntity> createNewRecords() throws ApiExcetionNeedToLog;

    Optional<DailyEntity> findAllByTitleIlike(String title);


}
