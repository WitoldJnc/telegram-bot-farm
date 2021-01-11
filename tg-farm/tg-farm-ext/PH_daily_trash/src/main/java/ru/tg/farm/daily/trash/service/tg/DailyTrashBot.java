package ru.tg.farm.daily.trash.service.tg;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tg.farm.common.exception.ApiExcetionNeedToLog;
import ru.tg.farm.daily.trash.model.DailyEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DailyTrashBot extends DailyBotSender {

    @Value("${ph.tg.api.bot.prod.name}")
    private String botName;

    @Value("${ph.tg.api.bot.prod.key}")
    private String botKey;

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.botKey;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update)  {
        doMainLogic(update);
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture) {
        val inlineKeyboardMarkup = new InlineKeyboardMarkup();
        val inlineKeyboardButton = new InlineKeyboardButton();
        val keyboardButtonsRow = new ArrayList<InlineKeyboardButton>();
        val rowList = new ArrayList<List<InlineKeyboardButton>>();

        inlineKeyboardButton.setText(capture);
        inlineKeyboardButton.setCallbackData("Button \"more\" has been pressed");
        keyboardButtonsRow.add(inlineKeyboardButton);

        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);


        return initSimpleSM(message, chatId, inlineKeyboardMarkup);
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture, Boolean showLink, String url) {
        return null;
    }

    @Override
    public void sendHelloMessage(Update update) throws ApiExcetionNeedToLog {
        try {
            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), AD + HELLO, "start"));
        } catch (TelegramApiException e) {
            throw new ApiExcetionNeedToLog("on init message \n" + e.toString());
        }
    }

    @Override
    public void sendMessageWhenCallback(Update update, Optional<DailyEntity> dailyEntity) throws ApiExcetionNeedToLog {
        try {
            execute(sendInlineKeyBoardMessage(update.getCallbackQuery().getMessage().getChatId(), dailyEntity.get().getTitle(), "more"));
        } catch (TelegramApiException e) {
            throw new ApiExcetionNeedToLog("on get callback by button \n" + e.toString());
        }
    }

    @Override
    public void sendFindMessage(Update update) throws ApiExcetionNeedToLog {
        try {
            DailyEntity findEntity = customDailyEntityService.findAllByTitleIlike(update.getMessage().getText())
                    .orElse(new DailyEntity("Увы, ничего нет \n" + AD));

            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), findEntity.getTitle(),
                    findEntity.getId() == null ? "start" : "more"));
        } catch (TelegramApiException e) {
            throw new ApiExcetionNeedToLog("on empty message and send ad \n" + e.toString());
        }
    }
}
