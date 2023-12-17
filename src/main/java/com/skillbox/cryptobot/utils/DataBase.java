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

    public void addUser (Message message){
        Long userId = message.getFrom().getId();
        if(!subscribersReposytory.existsByUserId(userId)){
        Subscribers subscribers = new Subscribers();
        subscribers.setUserId(userId);
        subscribersReposytory.saveAndFlush(subscribers);}
    }

    public void addUserPrice(Message message, Integer priceUser) {
        Long userId = message.getFrom().getId();
        Subscribers subscribers = subscribersReposytory.findByUserId(userId);
        subscribers.setPrice(priceUser);
        subscribersReposytory.saveAndFlush(subscribers);
    }
}