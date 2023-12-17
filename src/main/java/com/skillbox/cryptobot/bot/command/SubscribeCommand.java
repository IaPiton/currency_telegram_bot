package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.client.BinanceClient;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.utils.DataBase;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
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
    private DataBase dataBase = new DataBase();
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
        Integer priceUser = Integer.valueOf(Arrays.stream(arguments).findFirst().get());
        dataBase.addUserPrice(message, priceUser);
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        try{
            answer.setText("Текущая цена биткоина " + TextUtil.toString(service.getBitcoinPrice()) + " USD\n" +
                    "Новая подписка создана на стоимости " + priceUser + " USD");
            absSender.execute(answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (TelegramApiException e) {
            log.error("Error occurred in /subscribeCommand command", e);
        }
    }

    }
