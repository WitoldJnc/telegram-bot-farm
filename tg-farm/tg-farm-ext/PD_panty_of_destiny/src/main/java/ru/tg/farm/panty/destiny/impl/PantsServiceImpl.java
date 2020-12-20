package ru.tg.farm.panty.destiny.impl;

import ru.tg.farm.panty.destiny.model.ManPants;
import ru.tg.farm.panty.destiny.model.WomanPants;
import ru.tg.farm.panty.destiny.service.PantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Transactional
public class PantsServiceImpl implements PantsService {
    @Autowired
    private Environment env;

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public ManPants getRandomManPant() {
        return (ManPants) entityManager.createNativeQuery("select * from public.man_pants mp " +
                "where mp.has_using is false " +
                "order by random()" +
                "limit 1", ManPants.class)
                .getSingleResult();
    }

    @Override
    public WomanPants getRandomWomanPant() {
        return (WomanPants) entityManager.createNativeQuery("select * from public.woman_pants mp " +
                "where mp.has_using is false " +
                "order by random()" +
                "limit 1", WomanPants.class)
                .getSingleResult();
    }

    @Override
    public void changeManPantUsing(Integer id) {
        if (env.getProperty("tg.bot.api.chat").equals("@pantyOfDestiny")) {
            entityManager.createNativeQuery("update public.man_pants " +
                    "set has_using = true, dt = now()  where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public void changeWomanPantUsing(Integer id) {
        if (env.getProperty("tg.bot.api.chat").equals("@pantyOfDestiny")) {
            entityManager.createNativeQuery("update public.woman_pants " +
                    "set has_using = true, dt = now() where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public Integer getWomanLostPosts() {
        return Integer.valueOf(String.valueOf(
                entityManager.createNativeQuery("select count(*) from public.woman_pants " +
                        "where has_using = false")
                        .getSingleResult()
        ));
    }

    @Override
    public Integer getManLostPosts() {
        return Integer.valueOf(String.valueOf(entityManager.createNativeQuery("select count(*) from public.man_pants " +
                "where has_using = false")
                .getSingleResult()
        ));
    }

    @Override
    public List<ManPants> getLastFiveManPants() {
        return entityManager.createNativeQuery("select * from public.man_pants " +
                "where dt notnull " +
                "order by dt desc " +
                "limit 5", ManPants.class)
                .getResultList();
    }

    @Override
    public List<WomanPants> getLastFiveWomanPants() {
        return entityManager.createNativeQuery("select * from public.woman_pants " +
                "where dt notnull " +
                "order by dt desc " +
                "limit 5", WomanPants.class)
                .getResultList();
    }
}
