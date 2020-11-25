package ru.dailypron.bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.dailypron.bot.model.DailyEntity;
import ru.dailypron.bot.repo.CustomDailyEntityService;
import ru.dailypron.bot.repo.DBUpdateService;
import ru.dailypron.bot.repo.DailyEntityService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CustomDailyEntityServiceImpl implements CustomDailyEntityService {

    @Autowired
    private DailyEntityService dailyEntityService;

    @Autowired
    private DBUpdateService dbUpdateService;

    @Autowired
    private Environment environment;

    /**
     * чтобы не дергать кучу записей и снизить нагрузку на бд
     */
    @PersistenceContext
    public EntityManager entityManager;


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
    public Optional<DailyEntity> findRandom() {
        DailyEntity randEnity = (DailyEntity) entityManager.createNativeQuery("SELECT * FROM public.daily_entity de " +
                "ORDER BY RANDOM() " +
                "LIMIT 1", DailyEntity.class)
                .getSingleResult();
        return Optional.ofNullable(randEnity);
    }

    @Override
    public Optional<DailyEntity> findAny() {
        List<DailyEntity> allRecords = dailyEntityService.findAll();
        Collections.shuffle(allRecords);
        return allRecords.stream().findFirst();
    }


    @Override
    public Iterable<DailyEntity> deleteAllEntitiesByStatusIsTrue() {
        Iterable<DailyEntity> allByStatusIsTrue = dailyEntityService.findAllByStatusIsTrue();
        dailyEntityService.deleteAll(allByStatusIsTrue);
        return allByStatusIsTrue;
    }
}
