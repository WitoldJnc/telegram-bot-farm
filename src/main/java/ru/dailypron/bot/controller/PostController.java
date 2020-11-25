package ru.dailypron.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dailypron.bot.repo.ExceptionHandler;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private ExceptionHandler handler;

    @GetMapping("/")
    public void post() {
    }
}
