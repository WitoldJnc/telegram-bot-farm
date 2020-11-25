package ru.dailypron.bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

    @GetMapping("/")
    public ResponseEntity<String> getInfo(){
        return ResponseEntity.ok("checked");
    }
}
