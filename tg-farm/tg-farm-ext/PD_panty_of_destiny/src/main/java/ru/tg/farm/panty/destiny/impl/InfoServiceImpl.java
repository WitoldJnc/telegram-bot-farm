package ru.tg.farm.panty.destiny.impl;

import ru.tg.farm.panty.destiny.model.ManPants;
import ru.tg.farm.panty.destiny.model.ScheduleStatus;
import ru.tg.farm.panty.destiny.model.WomanPants;
import ru.tg.farm.panty.destiny.service.InfoService;
import ru.tg.farm.panty.destiny.service.PantsService;
import ru.tg.farm.panty.destiny.service.ScheduleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static ru.tg.farm.panty.destiny.model.ScheduleStatuses.*;

@Component
public class InfoServiceImpl implements InfoService {
    @Autowired
    private PantsService pantsService;
    @Autowired
    private ScheduleStatusService scheduleStatus;

    @Override
    public String getInfo() {
        final ScheduleStatus scheduleStatusByCode = scheduleStatus.getScheduleStatusByCode(MAN11.name());
        int a = 3;
        return  getLinks() + getSchedule() + getStatistic() + getLastImages();
    }

    private String getStatistic() {
        return "woman post lost : " + pantsService.getWomanLostPosts() + "</br>" +
                "man post lost : " + pantsService.getManLostPosts() + "</br> <hr> ";
    }

    private String getSchedule() {
        return "11 - man " +addStatus(MAN11.name()) +" </br>" +
                "15 - woman "+addStatus(WOMAN15.name())+"</br>" +
                "17 - man shiza "+addStatus(MANS17.name())+"</br>" +
                "21 - random "+addStatus(RANDOM21.name())+ "</br>" +
                " 23 - woman shiza "+addStatus(WOMANS23.name())+"</br> <hr> ";
    }

    private String getLinks() {
        return "<a href='https://panty-of-desteny.herokuapp.com/'> main </a></br>" +
                "<a href='https://panty-of-desteny.herokuapp.com/random'> random </a></br>" +
                "<a href='https://panty-of-desteny.herokuapp.com/man'> man </a></br>" +
                "<a href='https://panty-of-desteny.herokuapp.com/mans'> mans </a></br>" +
                "<a href='https://panty-of-desteny.herokuapp.com/woman'> woman </a></br>" +
                "<a href='https://panty-of-desteny.herokuapp.com/womans'> wmans </a></br><hr>";
    }

    private String getLastImages() {
        final List<ManPants> lastFiveManPants = pantsService.getLastFiveManPants();
        final List<WomanPants> lastFiveWomanPants = pantsService.getLastFiveWomanPants();
        StringBuilder sb = new StringBuilder("");
        sb.append("<h3>mans</h3>");
        lastFiveManPants.forEach(x -> sb.append("<img src='" + x.getUrl() + "' widt='170' height='170'><p>" + x.getId() + "</p> "));
        sb.append("<hr><h3>womans</h3>");
        lastFiveWomanPants.forEach(x -> sb.append("<img src='" + x.getUrl() + "' widt='170' height='170'><p>" + x.getId() + "</p> "));
        return sb.toString();
    }

    String addStatus(String code){
        return "| status: " + scheduleStatus.getScheduleStatusByCode(code).getStatus() +
                "| <a href='https://panty-of-desteny.herokuapp.com/" +code+ "'> change </a>";
    }
}
