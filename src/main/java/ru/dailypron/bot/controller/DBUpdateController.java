package ru.dailypron.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dailypron.bot.model.DailyEntity;
import ru.dailypron.bot.repo.CustomDailyEntityService;

import java.util.List;

@RestController
@RequestMapping("/db")
public class DBUpdateController {

    @Autowired
    private CustomDailyEntityService customDailyEntityService;


    @GetMapping("/delete")
    public ResponseEntity<Iterable<DailyEntity>> deleteOldEntities() {
        return ResponseEntity.ok(customDailyEntityService.deleteAllEntitiesByStatusIsTrue());
    }

    @GetMapping("/create")
    public ResponseEntity<Iterable<DailyEntity>> createNewRecords() {
        return ResponseEntity.ok(customDailyEntityService.createNewRecords());
    }
}
