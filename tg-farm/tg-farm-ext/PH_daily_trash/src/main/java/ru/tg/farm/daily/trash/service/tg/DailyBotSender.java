package ru.tg.farm.daily.trash.service.tg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tg.farm.common.repository.ExceptionHandler;
import ru.tg.farm.daily.trash.repo.CustomDailyEntityService;

public abstract class DailyBotSender extends TelegramLongPollingBot {
    @Autowired
    protected Environment env;
    @Autowired
    protected ExceptionHandler exceptionHandler;
    @Autowired
    protected CustomDailyEntityService customDailyEntityService;

    public abstract String getBotUsername();

    public abstract String getBotToken();

    public abstract void onUpdateReceived(Update update);

    public abstract SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture);

    public abstract SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture, Boolean showLink, String url);
}
