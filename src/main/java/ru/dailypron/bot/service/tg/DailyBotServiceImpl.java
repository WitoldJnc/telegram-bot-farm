package ru.dailypron.bot.service.tg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dailypron.bot.model.DailyEntity;
import ru.dailypron.bot.repo.CustomDailyEntityService;
import ru.dailypron.bot.repo.ExceptionHandler;
import ru.dailypron.bot.repo.tg.DailyBotService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DailyBotServiceImpl extends TelegramLongPollingBot implements DailyBotService {

    @Autowired
    private Environment env;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private CustomDailyEntityService customDailyEntityService;

    @Override
    public String getBotUsername() {
        return String.valueOf(env.getProperty("tg.bot.prod.api.name"));
    }

    @Override
    public String getBotToken() {
        return String.valueOf(env.getProperty("tg.bot.prod.api.key"));
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<DailyEntity> dailyEntity = Optional.of(customDailyEntityService.findRandom())
                .map(x -> customDailyEntityService.findRandom())
                .orElse(customDailyEntityService.findAny());

        String AD = "by @pantyOfDestiny \n\n";

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("/start")) {
                    try {
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), AD + "find your own guilty pleasure", "start"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                        exceptionHandler.postToInfo("on init message \n" + e);
                    }
                } else {
                    try {
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), AD, "start"));
                    } catch (TelegramApiException e) {
                        exceptionHandler.postToInfo("on empty message and send ad \n" + e);
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                execute(sendInlineKeyBoardMessage(update.getCallbackQuery().getMessage().getChatId(), dailyEntity.get().getTitle(), "more"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
                exceptionHandler.postToInfo("on get callback by button \n" + e);
            }
        }
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(capture);
        inlineKeyboardButton.setCallbackData("Button \"more\" has been pressed");
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage sm = new SendMessage();
        sm.setChatId(String.valueOf(chatId));
        sm.setText(String.format("<b>%s</b>", message));
        sm.setParseMode("HTML");
        sm.setReplyMarkup(inlineKeyboardMarkup);
        return sm;
    }
}
