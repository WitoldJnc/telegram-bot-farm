package ru.dailypron.bot.repo;

import org.jsoup.nodes.Node;
import ru.dailypron.bot.model.DailyEntity;

import java.util.List;
import java.util.Optional;

public interface DBUpdateService {
    List<DailyEntity> getNewDailyEntities();

    List<String> getTitles(int pageCount, String resouce);

    Boolean connectToResource(String cookie, String resouce);

    List<String> extractTitles(List<Node> nodesWithContent);

    List<Node> extractChildNodes(List<Node> childNodesForFilter);
}
