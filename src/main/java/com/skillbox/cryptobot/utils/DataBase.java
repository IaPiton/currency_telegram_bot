package com.skillbox.cryptobot.utils;

import com.skillbox.cryptobot.Model.Subscribers;
import com.skillbox.cryptobot.Reposytory.SubscribersReposytory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DataBase {
    @Autowired
    private SubscribersReposytory subscribersReposytory;

    public void addUser(Message message) {
        Long userId = message.getChatId();
        if (!subscribersReposytory.existsByUserId(userId)) {
            Subscribers subscribers = new Subscribers();
            subscribers.setUserId(userId);
            subscribersReposytory.saveAndFlush(subscribers);
        }
    }

    public void addUserSubscribers(Message message, Double priceUser) {
        Long userId = message.getChatId();
        Subscribers subscribers = subscribersReposytory.findByUserId(userId);
        subscribers.setPrice(priceUser);
        subscribersReposytory.saveAndFlush(subscribers);
    }

    public Double getUserSubscribers(Message message) {
        Long userId = message.getChatId();
        return subscribersReposytory.findByPrise(userId);
    }

    public String deleteSubscribe(Message message) {

        Long userId = message.getChatId();
        Subscribers subscribers = subscribersReposytory.findByUserId(userId);
        if (subscribers.getPrice() == null) {
            return "Активные подписки отсутствуют";
        }
        subscribers.setPrice(null);
        subscribersReposytory.saveAndFlush(subscribers);
        return "Подписка отменена";
    }
}
