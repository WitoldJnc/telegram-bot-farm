package ru.tg.farm.daily.trash.service.tg;

import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tg.farm.common.exception.ApiExcetionNeedToLog;
import ru.tg.farm.daily.trash.model.DailyEntity;
import ru.tg.farm.daily.trash.repo.CustomDailyEntityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DailyTrashPlusBot extends DailyBotSender {

    @Value("${ph.tg.api.bot.plus.name}")
    private String botName;

    @Value("${ph.tg.api.bot.plus.key}")
    private String botKey;


    @Autowired
    private CustomDailyEntityService customDailyEntityService;

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
    public SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture){
        return null;
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId, String message, String capture, Boolean showLink, String url) {
        val inlineKeyboardMarkup = new InlineKeyboardMarkup();
        val inlineKeyboardButton1 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText(capture);
        inlineKeyboardButton1.setCallbackData("Button \"more\" has been pressed");

        val keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton>();
        val keyboardButtonsRow2 = new ArrayList<InlineKeyboardButton>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);

        if (showLink) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText("show");
            button.setUrl(url);
            button.setCallbackData("show-callback");
            keyboardButtonsRow1.add(button);
        }

        val rowList = new ArrayList<List<InlineKeyboardButton>>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return initSimpleSM(message, chatId, inlineKeyboardMarkup);
    }

    @Override
    public void sendHelloMessage(Update update) throws ApiExcetionNeedToLog {
        try {
            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), AD + HELLO, "start", false, null));
        } catch (TelegramApiException e) {
            throw new ApiExcetionNeedToLog("plus_bot on init message \n" + e.toString());
        }
    }

    @Override
    public void sendMessageWhenCallback(Update update, Optional<DailyEntity> dailyEntity) throws ApiExcetionNeedToLog {
        if (!update.getCallbackQuery().getData().equals("show-callback")) {

        } try {
            execute(sendInlineKeyBoardMessage(update.getCallbackQuery().getMessage().getChatId(), dailyEntity.get().getTitle(), "more", true, dailyEntity.get().getUrl()));
        } catch (TelegramApiException e) {
            throw new ApiExcetionNeedToLog("plus_bot on get callback by button \n" + e.toString());
        }
    }

    @Override
    public void sendFindMessage(Update update) throws ApiExcetionNeedToLog {
        try {
            DailyEntity findEntity = customDailyEntityService.findAllByTitleIlike(update.getMessage().getText())
                    .orElse(new DailyEntity("Увы, ничего нет \n" + AD));
            final Boolean isEmpty = findEntity.getTitle().contains("Увы, ничего нет");

            execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(), findEntity.getTitle(), "start", !isEmpty,
                    isEmpty ? null : findEntity.getUrl()));

        } catch (TelegramApiException e) {
            throw new ApiExcetionNeedToLog("plus_bot on empty message and send ad \n" + e.toString());
        }
    }


}
