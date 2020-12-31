package ru.tg.farm.panty.destiny.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.tg.farm.common.exception.ApiExcetionNeedToLog;
import ru.tg.farm.panty.destiny.model.ManPants;
import ru.tg.farm.panty.destiny.model.WomanPants;
import ru.tg.farm.panty.destiny.service.ContentSender;
import ru.tg.farm.panty.destiny.service.HoroParser;
import ru.tg.farm.panty.destiny.service.ImageWriter;
import ru.tg.farm.panty.destiny.service.PantsService;

import java.io.File;
import java.util.Random;

@Component
@Slf4j
public class ContentSendeerImpl implements ContentSender {
    @Autowired
    private Environment env;
    @Autowired
    private HoroParser horoParser;
    @Autowired
    private ImageWriter imageWriter;
    @Autowired
    private PantsService pantsService;

    @Override
    public void sendManContent() throws ApiExcetionNeedToLog {
        try {
            final ManPants randomManPant = pantsService.getRandomManPant();
            send(imageWriter.write(randomManPant.getUrl()),
                    horoParser.getHoro());
            pantsService.changeManPantUsing(randomManPant.getId());
        } catch (Exception e) {
            throw new ApiExcetionNeedToLog("on send man content " + e.toString());
        }

    }

    @Override
    public void sendWomanContent() throws ApiExcetionNeedToLog {
        try {
            final WomanPants randomWomanPant = pantsService.getRandomWomanPant();
            send(imageWriter.write(randomWomanPant.getUrl()),
                    horoParser.getHoro());
            pantsService.changeWomanPantUsing(randomWomanPant.getId());
        } catch (Exception e) {
            throw new ApiExcetionNeedToLog("on send woman content " + e.toString());
        }
    }

    @Override
    public void sendManWeird() throws ApiExcetionNeedToLog {
        try {
            final ManPants randomManPant = pantsService.getRandomManPant();
            send(imageWriter.write(randomManPant.getUrl()),
                    horoParser.getShiza());
            pantsService.changeManPantUsing(randomManPant.getId());
        } catch (Exception e) {
            throw new ApiExcetionNeedToLog("on send random man content " + e.toString());
        }

    }

    @Override
    public void sendWomanWeird() throws ApiExcetionNeedToLog {
        try {
            final WomanPants randomWomanPant = pantsService.getRandomWomanPant();

            send(imageWriter.write(randomWomanPant.getUrl()),
                    horoParser.getShiza());
            pantsService.changeWomanPantUsing(randomWomanPant.getId());
        } catch (Exception e) {
            throw new ApiExcetionNeedToLog("on send random woman content " + e.toString());
        }
    }

    @Override
    public void sendRandomContent() throws ApiExcetionNeedToLog {
        final WomanPants randomWomanPant = pantsService.getRandomWomanPant();
        final ManPants randomManPant = pantsService.getRandomManPant();
        String content = "";
        Random randomOne = new Random();
        int one = randomOne.nextInt(7491251);
        if (one % 2 == 0) {
            content = randomWomanPant.getUrl();
            pantsService.changeWomanPantUsing(randomWomanPant.getId());
        } else {
            content = randomManPant.getUrl();
            pantsService.changeManPantUsing(randomManPant.getId());
        }
        send(imageWriter.write(content), one % 4 == 0 && one < 90000
                ? horoParser.getShiza()
                : horoParser.getHoro());
    }

    private void send(String pantsPhotoUrl, String caption) throws ApiExcetionNeedToLog {
        try {
            TelegramBot bot = new TelegramBot(env.getProperty("pod.tg.api.bot.key"));

            File file = new File(pantsPhotoUrl);

            SendPhoto photo = new SendPhoto(env.getProperty("pod.tg.api.bot.chat"), file)
                    .caption(caption)
                    .parseMode(ParseMode.HTML);

            bot.execute(photo);
        } catch (Exception e) {
            throw new ApiExcetionNeedToLog("on send pant " + e.toString());
        }
    }

}

