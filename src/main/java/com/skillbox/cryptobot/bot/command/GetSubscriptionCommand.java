package com.skillbox.cryptobot.bot.command;


import com.skillbox.cryptobot.utils.DataBaseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
@Slf4j
@AllArgsConstructor
public class GetSubscriptionCommand implements IBotCommand {
    private DataBaseUtil dataBaseUtil;

    @Override
    public String getCommandIdentifier() {
        return "get_subscription";
    }

    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Double userSubscribers = dataBaseUtil.getUserSubscribers(message);
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        try {
            if (userSubscribers == null) {
                answer.setText("Активные подписки отсутствуют");
            } else {
                answer.setText("Вы подписаны на стоимость биткоина " + userSubscribers + " USD");
            }
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /s/get_subscription command", e);
        }
    }

}