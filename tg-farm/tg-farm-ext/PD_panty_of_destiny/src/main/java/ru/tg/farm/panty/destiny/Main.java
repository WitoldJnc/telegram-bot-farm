package ru.tg.farm.panty.destiny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("ru.tg.farm.*")
@PropertySource(value = {"application.properties", "common.properties"}, ignoreResourceNotFound = true)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}

