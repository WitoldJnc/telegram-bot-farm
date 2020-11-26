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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PlusBot extends TelegramLongPollingBot {
    @Autowired
    private Environment env;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private CustomDailyEntityService customDailyEntityService;

    @Override
    public String getBotUsername() {
        return String.valueOf(env.getProperty("tg.bot.plus.api.name"));
    }

    @Override
    public String getBotToken() {
        return String.valueOf(env.getProperty("tg.bot.plus.api.key"));
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
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), AD + "find your own guilty pleasure", "start", false, null));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                        exceptionHandler.postToInfo("on init message \n" + e);
                    }
                } else {
                    try {
                        //todo refactoring
                        Optional<DailyEntity> allByTitleIlike = customDailyEntityService.findAllByTitleIlike(update.getMessage().getText());
                        if(!allByTitleIlike.isEmpty()){
                            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), allByTitleIlike.get().getTitle(), "start", true, allByTitleIlike.get().getUrl()));
                        } else {
                            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), "Увы, ничего нет " + AD, "start", false, null));
                        }
                    } catch (TelegramApiException e) {
                        exceptionHandler.postToInfo("on empty message and send ad \n" + e);
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            if (!update.getCallbackQuery().getData().equals("show-callback")) {

            } try {
                execute(sendInlineKeyBoardMessage(update.getCallbackQuery().getMessage().getChatId(), dailyEntity.get().getTitle(), "more", true, dailyEntity.get().getUrl()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
                exceptionHandler.postToInfo("on get callback by button \n" + e);
            }
        }
    }


    public static SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture, Boolean showLink, String url) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText(capture);
        inlineKeyboardButton1.setCallbackData("Button \"more\" has been pressed");


        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);

        if (showLink) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("show");
            button.setUrl(url);
            button.setCallbackData("show-callback");
            keyboardButtonsRow1.add(button);
        }

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sm = new SendMessage();
        sm.setChatId(String.valueOf(chatId));
        sm.setText(String.format("<b>%s</b>", message));
        sm.setParseMode("HTML");
        sm.setReplyMarkup(inlineKeyboardMarkup);
        return sm;
    }
}
