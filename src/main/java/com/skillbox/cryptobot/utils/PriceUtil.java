package com.skillbox.cryptobot.utils;


import com.skillbox.cryptobot.bot.SendMessages;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import com.skillbox.cryptobot.service.CryptoCurrencyService;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.awt.desktop.AppReopenedEvent;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


@RequiredArgsConstructor
@Component
public final class PriceUtil {
    private final SubscribersRepository subscribersRepository;
    private final CryptoCurrencyService cryptoCurrencyService;

    private Set<Long> userIdSet = new TreeSet<>();
    public static Double price;
    @Value("${update.getSubscribeTime}")
    private  Duration subscribeTime;
    @Value("${telegram.bot.token}")
    private  String token;
    @Value("${telegram.bot.username}")
    private  String username;

    @EventListener(AppReopenedEvent.class)
    @Scheduled(fixedRateString = "${update.getPriceTime}")
    public Double getPrice() throws IOException {
        price = cryptoCurrencyService.getBitcoinPrice();
        List<Subscribers> subscribers = subscribersRepository.findAll();
        for (Subscribers subscriber : subscribers) {
            if (subscriber.getPrice() != null && subscriber.getPrice() > price) {
                mailingMessage(subscriber);
            }
            checkUser();
        }
        return price;
    }

    private void checkUser() {
        for (Long userId : userIdSet) {
            Subscribers subscribers = subscribersRepository.findByUserId(userId);
            if (subscribers.getPrice() == null || subscribers.getPrice() < price) {
                userIdSet.remove(userId);
            }
        }
    }

    private void mailingMessage(Subscribers subscriber) {
        if (!userIdSet.contains(subscriber.getUserId())) {
            userIdSet.add(subscriber.getUserId());
            Thread thread = new Thread(new SendMessages(
                    subscriber.getUserId(),
                    username,
                    token,
                    subscribersRepository,
                    subscribeTime));
            thread.start();
        }
    }
}




