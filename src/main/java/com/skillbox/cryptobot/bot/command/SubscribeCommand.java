package com.skillbox.cryptobot.bot.command;


import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.utils.DataBase;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Arrays;

/**
 * Обработка команды подписки на курс валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class SubscribeCommand implements IBotCommand {
    private final CryptoCurrencyService service;
    private DataBase dataBase;

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        String setMessage = null;
        try {
            setMessage = setMessage(message, arguments);

            answer.setChatId(message.getChatId());
            answer.setText(setMessage);
            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String setMessage(Message message, String[] arguments) throws IOException {

        try {
            Double priceUser = Double.valueOf(Arrays.stream(arguments).findFirst().get());
            dataBase.addUserSubscribers(message, priceUser);
            return "Текущая цена биткоина " + TextUtil.toString(service.getBitcoinPrice()) + " USD\n" +
                    "Новая подписка создана на стоимости " + priceUser + " USD";
        } catch (NumberFormatException e) {
            return "Некоректно введена сума подписки";
        }
    }
}
