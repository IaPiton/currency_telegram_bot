package com.skillbox.cryptobot.bot;

import com.skillbox.cryptobot.model.Subscribers;
import com.skillbox.cryptobot.repository.SubscribersRepository;
import com.skillbox.cryptobot.utils.PriceUtil;
import com.skillbox.cryptobot.utils.TextUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component

public class SendMessages extends Thread {
    private   Long id;
    private   String botToken;
    private  String botUserName;
    private   SubscribersRepository subscribersRepository;
    private   Duration subscribeTime;

    public SendMessages(){

    }
public SendMessages(Long id,
                    String botToken,
                    String botUserName,
                    SubscribersRepository subscribersRepository,
                    Duration subscribeTime ){
    this.id = id;
    this.botToken = botToken;
    this.botUserName = botUserName;
    this.subscribersRepository = subscribersRepository;
    this.subscribeTime = subscribeTime;
}
    private List<IBotCommand> commandsList = new ArrayList<>();

        @Override
    public void run() {
        CryptoBot bot = new CryptoBot(botUserName, botToken, addIBotCommand());
        while (true) {
                Subscribers subscribers = subscribersRepository.findByUserId(id);
                if (subscribers.getPrice() == null || subscribers.getPrice() < PriceUtil.price) {
                    break;
                }
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(id);
                sendMessage.setText("Пора покупать, стоимость биткоина " + String.format("%.3f", PriceUtil.price) + " USD");
            try {
                bot.execute(sendMessage);
                Thread.sleep(subscribeTime);
            } catch (InterruptedException | TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public List<IBotCommand> addIBotCommand() {
        commandsList.add(new BotCommand("/send", "message") {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
            }
        });
        return commandsList;
    }
}
