package ru.dailypron.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dailypron.bot.model.DailyEntity;
import ru.dailypron.bot.repo.DBUpdateService;

import java.util.List;

@RestController
@RequestMapping("/db")
public class DBUpdateController {

    @Autowired
    private DBUpdateService dbUpdateService;

    @GetMapping("/")
    public List<DailyEntity> t(){
        return dbUpdateService.createDailyEntities();
    }
}
