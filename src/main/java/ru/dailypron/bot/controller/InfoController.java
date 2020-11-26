package ru.dailypron.bot.controller;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Swagger2DemoRestController", description = "info about last records")
@RestController
@RequestMapping("/information")
public class InfoController {

    @GetMapping("/getInfo")
    public ResponseEntity<String> getInfo(){
        return ResponseEntity.ok("checked");
    }
}
