package com.skillbox.cryptobot.utils;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@AllArgsConstructor
public class DataBaseUtil {

    private final SubscribersRepository subscribersRepository;

    public void addUser(Message message) {
        Long userId = message.getChatId();
        if (!subscribersRepository.existsByUserId(userId)) {
            Subscribers subscribers = new Subscribers();
            subscribers.setUserId(userId);
            subscribersRepository.saveAndFlush(subscribers);
        }
    }

    public void addUserSubscribers(Message message, Double priceUser) {
        Long userId = message.getChatId();
        Subscribers subscribers = subscribersRepository.findByUserId(userId);
        subscribers.setPrice(priceUser);
        subscribersRepository.saveAndFlush(subscribers);
    }

    public Double getUserSubscribers(Message message) {
        Long userId = message.getChatId();
        return subscribersRepository.findByPrise(userId);
    }

    public String deleteSubscribe(Message message) {

        Long userId = message.getChatId();
        Subscribers subscribers = subscribersRepository.findByUserId(userId);
        if (subscribers.getPrice() == null) {
            return "Активные подписки отсутствуют";
        }
        subscribers.setPrice(null);
        subscribersRepository.saveAndFlush(subscribers);
        return "Подписка отменена";
    }
}