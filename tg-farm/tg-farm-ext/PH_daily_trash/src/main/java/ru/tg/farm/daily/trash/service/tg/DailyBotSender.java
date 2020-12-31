package ru.tg.farm.daily.trash.service.tg;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.tg.farm.common.exception.ApiExcetionNeedToLog;
import ru.tg.farm.daily.trash.model.DailyEntity;
import ru.tg.farm.daily.trash.repo.CustomDailyEntityService;

import java.util.Optional;

public abstract class DailyBotSender extends TelegramLongPollingBot {
    @Autowired
    protected CustomDailyEntityService customDailyEntityService;

    protected static final String AD = "by @pantyOfDestiny \n\n";
    protected static final String HELLO = "find your own guilty pleasure";

    public abstract String getBotUsername();

    public abstract String getBotToken();

    public abstract void onUpdateReceived(Update update);

    public abstract SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture);

    public abstract SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture, Boolean showLink, String url);

    public abstract void sendHelloMessage(Update update) throws ApiExcetionNeedToLog;

    public abstract void sendMessageWhenCallback(Update update, Optional<DailyEntity> dailyEntity) throws ApiExcetionNeedToLog;

    public abstract void sendFindMessage(Update update) throws ApiExcetionNeedToLog;

    protected Optional<DailyEntity> getAnyDailyEntity() {
        return Optional.of(customDailyEntityService.findRandom())
                .map(x -> customDailyEntityService.findRandom())
                .orElse(customDailyEntityService.findAny());
    }

    public void doMainLogic(Update update) throws ApiExcetionNeedToLog {
        Optional<DailyEntity> dailyEntity = getAnyDailyEntity();

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")) {
                    sendHelloMessage(update);
                } else {
                    sendFindMessage(update);
                }
            }
        } else if (update.hasCallbackQuery()) {
            sendMessageWhenCallback(update, dailyEntity);
        }
    }

    public SendMessage initSimpleSM(String message, Long chatId, InlineKeyboardMarkup markup){
        SendMessage sm = new SendMessage();
        sm.setChatId(String.valueOf(chatId));
        sm.setText(String.format("<b>%s</b>", message));
        sm.setParseMode("HTML");
        sm.setReplyMarkup(markup);
        return sm;
    }
}
