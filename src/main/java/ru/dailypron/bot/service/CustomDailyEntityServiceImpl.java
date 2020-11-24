package ru.dailypron.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.dailypron.bot.model.DailyEntity;
import ru.dailypron.bot.repo.CustomDailyEntityService;
import ru.dailypron.bot.repo.DBUpdateService;
import ru.dailypron.bot.repo.DailyEntityService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomDailyEntityServiceImpl implements CustomDailyEntityService {

    @Autowired
    private DailyEntityService dailyEntityService;

    @Autowired
    private DBUpdateService dbUpdateService;

    @Autowired
    private Environment environment;


    @Override
    public List<DailyEntity> createNewRecords() {
        List<DailyEntity> newDailyEntities = dbUpdateService.getNewDailyEntities();
        dailyEntityService.saveAll(newDailyEntities);
        return newDailyEntities;
    }

    @Override
    public void changeEntityStatus(DailyEntity entity) {
        entity.setStatus(true);
        dailyEntityService.save(entity);
    }

    @Override
    public Iterable<DailyEntity> deleteAllEntitiesByStatusIsTrue() {
        Iterable<DailyEntity> allByStatusIsTrue = dailyEntityService.findAllByStatusIsTrue();
        dailyEntityService.deleteAll(allByStatusIsTrue);
        return allByStatusIsTrue;
    }
}
