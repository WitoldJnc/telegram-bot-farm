package ru.dailypron.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.dailypron.bot.model.DailyEntity;
import ru.dailypron.bot.repo.CustomDailyEntityService;
import ru.dailypron.bot.repo.DBUpdateService;
import ru.dailypron.bot.repo.DailyEntityService;

import java.util.Optional;

@Component
public class CustomDailyEntityServiceImpl implements CustomDailyEntityService {

    @Autowired
    private DailyEntityService dailyEntityService;

    @Autowired
    private DBUpdateService dbUpdateService;

    @Autowired
    private Environment environment;


    @Override
    public Iterable<DailyEntity> createNewRecords() {
        Iterable<DailyEntity> newDailyEntities = dbUpdateService.getNewDailyEntities();
        dailyEntityService.saveAll(newDailyEntities);
        return newDailyEntities;
    }

    @Override
    public Optional<DailyEntity> changeEntityStatus(DailyEntity entity) {
        entity.setStatus(true);
        dailyEntityService.save(entity);
        return Optional.of(entity);
    }

    @Override
    public Iterable<DailyEntity> deleteAllEntitiesByStatusIsTrue() {
        Iterable<DailyEntity> allByStatusIsTrue = dailyEntityService.findAllByStatusIsTrue();
        dailyEntityService.deleteAll(allByStatusIsTrue);
        return allByStatusIsTrue;
    }
}
