package ru.tg.farm.panty.destiny.controller;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ContentCreateController {

    @SneakyThrows
    @GetMapping("/url")
    public List<String> getUrlsFromPage(@RequestParam("url") String address) {
        Document doc = Jsoup.connect(address)
                .get();
        Elements pngs = doc.getElementsByTag("img");
        List<String> urls = new ArrayList<>();

        pngs.stream()
                .forEach(x -> {
                    urls.add(x.attr("src"));
                });
        return urls;
    }

}
