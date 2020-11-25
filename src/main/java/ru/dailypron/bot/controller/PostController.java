package ru.dailypron.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dailypron.bot.model.DailyEntity;
import ru.dailypron.bot.repo.CustomDailyEntityService;
import ru.dailypron.bot.repo.DailyEntityService;
import ru.dailypron.bot.repo.ExceptionHandler;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private ExceptionHandler handler;

    @Autowired
    private DailyEntityService dailyEntityService;

    @Autowired
    private CustomDailyEntityService customDailyEntityService;

    @GetMapping("/")
    public ResponseEntity<DailyEntity> post() {
        return ResponseEntity.ok(customDailyEntityService.findAny().get());
    }
}
