package ru.dailypron.bot.repo;

import org.jsoup.nodes.Node;
import ru.dailypron.bot.model.DailyEntity;

import java.util.List;
import java.util.Map;

public interface DBUpdateService {
    List<DailyEntity> getNewDailyEntities();

    Map<String, String> getTitles(int pageCount, String resouce);

    Boolean connectToResource(String cookie, String resouce);

    Map<String, String> extractTitles(List<Node> nodesWithContent);

    List<Node> extractChildNodes(List<Node> childNodesForFilter);
}
