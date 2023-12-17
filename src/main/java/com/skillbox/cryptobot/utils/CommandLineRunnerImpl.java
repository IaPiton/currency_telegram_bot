package com.skillbox.cryptobot.utils;


import com.skillbox.cryptobot.Model.Subscribers;
import com.skillbox.cryptobot.Reposytory.SubscribersReposytory;
import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.timedbot.TimedSendLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.name.BotName;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;


import java.io.IOException;
import java.util.List;


@Component
@AllArgsConstructor
@EnableScheduling

public final class CommandLineRunnerImpl implements CommandLineRunner {


    private SubscribersReposytory subscribersReposytory;
    private final CryptoCurrencyService service;
    @Getter
    private static double getPrise = 0.0;

    @Override
    public void run(String... args) throws Exception {
        getPrise = setGetPrise();
        getNotificationSubscribe();
    }

    @Scheduled(fixedRateString = "${update.getPriceTime}")
    public Double setGetPrise() throws IOException {
        getPrise = service.getBitcoinPrice();
        System.out.println(getPrise);
        return getPrise;
    }


    @Scheduled(fixedRateString = "${update.getSubscribeTime}")
    public void getNotificationSubscribe() {
        List<Subscribers> subscribersList = subscribersReposytory.findAll();
        for (Subscribers subscribers : subscribersList) {
            Long chathId = subscribers.getUserId();
            String text = "Пора покупать, стоимость биткоина " + getPrise;

        }


    }
}




