package ru.dailypron.bot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.dailypron.bot.model.DailyEntity;
import ru.dailypron.bot.repo.DBUpdateService;
import ru.dailypron.bot.repo.DailyEntityService;
import ru.dailypron.bot.repo.ExceptionHandler;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        List<String> titles = getTitles(Integer.parseInt(String.valueOf(env.getProperty("api.page.count"))), resource);

        Iterable<DailyEntity> allByStatusIsFalse = dailyEntityService.findAllByStatusIsFalse();

        //todo упростить.
        allByStatusIsFalse.forEach(entity -> {
            titles.forEach(daily -> {
                if (!entity.getTitle().equals(daily)) {
                    dailyEntities.add(new DailyEntity(daily));
                }
            });
        });

        return dailyEntities;
    }

    @Override
    public List<String> getTitles(int pageCount, String resource) {
        List<String> titles = new ArrayList<>();
        try {

            for (int p = 1; p < pageCount; p++) {
                Document document = Jsoup.connect(resource + "/video?o=mv&cc=ru&page=" + p).get();
                List<Node> childNodesForFilter = document.getElementById("videoCategory").childNodes();
                List<Node> nodesWithContent = extractChildNodes(childNodesForFilter);
                List<String> titlesPerPage = extractTitles(nodesWithContent);
                titles.addAll(titlesPerPage);
                Thread.sleep(800);
                titles.stream().distinct().collect(Collectors.toList());

            }
        } catch (IOException | InterruptedException e) {
            titles = null;
            exceptionHandler.postToInfo("on get titles " + e.getMessage());
        }

        return titles;
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

    public List<String> extractTitles(List<Node> nodesWithContent) {
        List<String> titlesPerPage = new ArrayList<>();
        nodesWithContent
                .forEach(x -> {
                    String content = x.childNode(1).childNode(5).childNode(1).childNode(1).attr("title");
                    if (Pattern.matches(".*\\p{InCyrillic}.*", content)) {
                        titlesPerPage.add(content);
                    }
                });
        return titlesPerPage;
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



