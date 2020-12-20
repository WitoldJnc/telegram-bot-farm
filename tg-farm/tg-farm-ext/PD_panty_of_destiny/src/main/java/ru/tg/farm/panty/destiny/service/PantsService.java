package ru.tg.farm.panty.destiny.service;


import ru.tg.farm.panty.destiny.model.ManPants;
import ru.tg.farm.panty.destiny.model.WomanPants;

import java.util.List;

public interface PantsService {
    ManPants getRandomManPant();

    WomanPants getRandomWomanPant();

    void changeManPantUsing(Integer id);

    void changeWomanPantUsing(Integer id);

    Integer getWomanLostPosts();

    Integer getManLostPosts();

    List<ManPants> getLastFiveManPants();

    List<WomanPants> getLastFiveWomanPants();
}
