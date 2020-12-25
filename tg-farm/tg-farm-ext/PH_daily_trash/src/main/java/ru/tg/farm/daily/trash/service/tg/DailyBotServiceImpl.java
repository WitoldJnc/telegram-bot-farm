package ru.tg.farm.daily.trash.service.tg;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tg.farm.daily.trash.model.DailyEntity;
import ru.tg.farm.daily.trash.repo.tg.DailyBotService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DailyBotServiceImpl extends DailyBotSender implements DailyBotService {

    @Override
    public String getBotUsername() {
        return String.valueOf(env.getProperty("ph.tg.api.bot.prod.name"));
    }

    @Override
    public String getBotToken() {
        return String.valueOf(env.getProperty("ph.tg.api.bot.prod.key"));
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
                        DailyEntity findEntity = customDailyEntityService.findAllByTitleIlike(update.getMessage().getText())
                                .orElse(new DailyEntity("Увы, ничего нет " + AD));

                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), findEntity.getTitle(), "start"));
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

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture) {
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

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture, Boolean showLink, String url) {
        return null;
    }
}
