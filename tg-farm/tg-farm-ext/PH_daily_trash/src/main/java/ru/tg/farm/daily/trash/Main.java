package ru.tg.farm.daily.trash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableDiscoveryClient
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("ru.tg.farm.*")
@PropertySource(value = {"application.yml", "common.yml", "bootstrap.yml"}, ignoreResourceNotFound = true)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}


