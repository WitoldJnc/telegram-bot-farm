package ru.tg.farm.daily.trash.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.tg.farm.daily.trash.model.DailyEntity;
import ru.tg.farm.daily.trash.repo.DBUpdateService;
import ru.tg.farm.daily.trash.repo.DailyEntityService;
import ru.tg.farm.common.repository.ExceptionHandler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class DBUpdateServiceImpl implements DBUpdateService {
    @Autowired
    private Environment env;
    @Autowired
    private ExceptionHandler exceptionHandler;
    @Autowired
    private DailyEntityService dailyEntityService;


    @Override
    public List<DailyEntity> getNewDailyEntities() {
        String cookie = env.getProperty("api.bot.cookie");
        String resource = env.getProperty("api.bot.resource");
        connectToResource(cookie, resource);
        List<DailyEntity> dailyEntities = new ArrayList<>();
        Map<String, String> titles = getTitles(Integer.parseInt(String.valueOf(env.getProperty("api.page.count"))), resource);

        titles.forEach((key, value) -> dailyEntities.add(new DailyEntity(key, value)));

        return dailyEntities;
    }

    @Override
    public Map<String, String> getTitles(int pageCount, String resource) {
        Map<String, String> dailyContent = new HashMap<>();
        try {

            for (int p = 1; p < pageCount; p++) {
                System.out.println(p);
                Document document = Jsoup.connect(resource + "/video?o=mv&cc=ru&page=" + p).get();
                List<Node> childNodesForFilter = document.getElementById("videoCategory").childNodes();
                List<Node> nodesWithContent = extractChildNodes(childNodesForFilter);
                Map<String, String> content = extractTitles(nodesWithContent);
                dailyContent.putAll(content);
                Thread.sleep(800);

            }
        } catch (IOException | InterruptedException e) {
            dailyContent = new HashMap<>();
            exceptionHandler.postToInfo("on get titles " + e.getMessage());
        }

        return dailyContent;
    }

    public List<Node> extractChildNodes(List<Node> childNodesForFilter) {
        ArrayList<Node> nodesWithContent = new ArrayList<>();
        for (int i = 0; i < childNodesForFilter.size(); i++) {
            if ((i % 2 != 0) && (childNodesForFilter.get(i).attributes().size() != 1)) {
                nodesWithContent.add(childNodesForFilter.get(i));
            }
        }
        return nodesWithContent;
    }

    public Map<String, String> extractTitles(List<Node> nodesWithContent) {
        Map<String, String> contentPerPage = new HashMap<>();
        nodesWithContent
                .forEach(x -> {
                    String content = x.childNode(1).childNode(5).childNode(1).childNode(1).attr("title");
                    String href = env.getProperty("api.bot.resource") + x.childNode(1).childNode(5).childNode(1).childNode(1).attr("href");
                    if (Pattern.matches(".*\\p{InCyrillic}.*", content)) {
                        contentPerPage.put(content, href);
                    }
                });
        return contentPerPage;
    }

    @Override
    public Boolean connectToResource(String cookie, String resource) {
        try {
            URL url = new URL(resource);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Cookie", cookie);
            conn.connect();
            return true;
        } catch (Exception e) {
            exceptionHandler.postToInfo("cant connect to resource");
            return false;
        }
    }

}



