package ru.tg.bot.farm;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ru.tg.farm")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


}




