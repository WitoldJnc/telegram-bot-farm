package ru.tg.farm.panty.destiny.impl;

import ru.tg.farm.panty.destiny.model.Sign;
import ru.tg.farm.panty.destiny.pool.HoroPool;
import ru.tg.farm.panty.destiny.service.HoroParser;
import ru.tg.farm.panty.destiny.service.HoroSignService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HoroParserImpl implements HoroParser {

    @Autowired
    private HoroPool horoPool;
    @Autowired
    private HoroSignService horoSignService;

    private List<String> exc = new ArrayList<>();

    @Override
    public String getHoro() {
        if (exc.size() < 12) {
            final List<String> randomHoroUrl = getRandomHoroUrl();
            List<String> horos = new ArrayList<>();
            randomHoroUrl.forEach(x -> horos.add(choiceParse(x)));
            return createHoro(horos);
        } else {
            exc = new ArrayList<>();
            final List<String> randomHoroUrl = getRandomHoroUrl();
            final String uraculUrl = randomHoroUrl.stream().filter(x -> x.contains("oracul")).collect(Collectors.toList()).get(0);
            return choiceParse(uraculUrl);
        }
    }

    @Override
    public String getShiza() {
        return parseShiza(getRandomShizaUrl());
    }

    private String createHoro(List<String> horos) {
        Map<String, Integer> hrs = new HashMap<>();
        horos.forEach(x -> {
            hrs.put(x, StringUtils.countMatches(x, "."));
        });
        final List<String> sorted = hrs.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();

        String firstSentOfFirstEl = getFirstSent(sorted.get(0));

        String twiceSentOfTwiceEl = getFirstSent(sorted.get(1).replace(getFirstSent(sorted.get(1)), " ").trim());

        String thirdElWithoutFirstSent = sorted.get(2).replace(getFirstSent(sorted.get(2)), "").trim();
        String thirdSentOfThirdEl = getFirstSent(thirdElWithoutFirstSent.replace(getFirstSent(thirdElWithoutFirstSent), "").trim());

        String fourElWithoutFirstSent = sorted.get(3).replace(getFirstSent(sorted.get(3)), "").trim();
        String fourElWitoutTwiceSent = fourElWithoutFirstSent.replace(getFirstSent(fourElWithoutFirstSent), "").trim();
        String fourElWithoutThirdSent = fourElWitoutTwiceSent.replace(getFirstSent(fourElWitoutTwiceSent), "").trim();
        String fourSentOfFourEl = getFirstSent(fourElWithoutThirdSent);

        sb
                .append(twiceSentOfTwiceEl).append(" ")
                .append(fourSentOfFourEl).append(" ")
                .append(firstSentOfFirstEl).append(" ")
                .append(thirdSentOfThirdEl).append(" ");

        return sb.toString();
    }

    private String getFirstSent(String str) {
        return str.substring(0, str.indexOf(".") + 1).trim();
    }

    private String choiceParse(String url) {
        String content = "";
        try {
            if (url.contains("https://orakul.com")) {
                content = parseOracul(url);
            }
            if (url.contains("https://1001goroskop.ru")) {
                content = parse1001goroskop(url);
            }
            if (url.contains("www.wday.ru")) {
                content = parseWday(url);
            }
            if (url.contains("goroskop365.ru")) {
                content = parseGoroskop365(url);
            }
            return content;
        } catch (Exception e) {
            log.error(e.toString());
            exc.add(e.toString());
            return getHoro();
        }
    }

    private List<String> getRandomHoroUrl() {
        checkFill();
        Sign sign = horoPool.getRandomSign();
        List<String> url = new ArrayList<>();
        boolean flag = true;
        while (flag) {
            if (!horoSignService.contains(sign.toString())) {
                horoSignService.add(sign.toString());
                url.addAll(horoPool.getHoros().get(sign));
                flag = false;
            } else {
                sign = horoPool.getRandomSign();
            }
        }
        return url;
    }

    private String getRandomShizaUrl() {
        checkFill();
        Sign sign = horoPool.getRandomSign();
        String url = "";
        boolean flag = true;
        while (flag) {
            if (!horoSignService.contains(sign.toString())) {
                horoSignService.add(sign.toString());
                url = horoPool.getShiza().get(sign);
                flag = false;
            } else {
                sign = horoPool.getRandomSign();
            }
        }
        return url;
    }


    private void checkFill() {
        if (horoSignService.getFill() == horoPool.getInitList().size()) {
            horoSignService.truncate();
        }
    }

    public String parseOracul(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String content = document.select("#content > div > div > div > div > div > p").text();
        content = content.replace(" поделиться в соцсетях", "");
        return content.trim();
    }

    private String parse1001goroskop(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String horo = document.getElementsByTag("tbody").text();
        horo = horo.replace("Гороскоп на сегодня: Дмитрий Зима", "");
        return horo.trim();
    }

    private String parseWday(String url) throws IOException {
        Document j = Jsoup.connect(url).get();
        return j.select("#wrap > div > div> div> div> div > div > div > p").text();
    }

    private String parseGoroskop365(String url) throws IOException {
        Document j = Jsoup.connect(url).get();
        return j.select("#content_wrapper > div").get(0).text();
    }

    private String parseShiza(String randomShizaUrl) {
        try {
            Document document = Jsoup.connect(randomShizaUrl).get();
            return document.select("div.text-link").text();
        } catch (Exception e) {
            log.error(e.toString());
            return getHoro();
        }
    }

}
