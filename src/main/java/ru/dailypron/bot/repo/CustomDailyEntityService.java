package ru.dailypron.bot.repo;

import ru.dailypron.bot.model.DailyEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CustomDailyEntityService {
    void changeEntityStatus(DailyEntity entity);

    Iterable<DailyEntity> deleteAllEntitiesByStatusIsTrue();

    List<DailyEntity> createNewRecords();
}
