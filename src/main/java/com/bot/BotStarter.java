package com.bot;

import com.bot.components.ScheduledTasks;
import com.bot.components.TelegramClient;
import com.bot.exceptions.StarterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class BotStarter {

    private static final Logger log = LogManager.getLogger(BotStarter.class);

    public static void main(String[] args) throws StarterException {
        try {
            ApiContextInitializer.init();
            registerBot(TelegramClient.getInstance());
            new ScheduledTasks().run();
        } catch (TelegramApiRequestException e) {
            log.error("Can't start bot assistant", e);
            throw new StarterException(e.getMessage(), e);
        }
    }

    private static void registerBot(TelegramClient telegramClient) throws TelegramApiRequestException {
        new TelegramBotsApi().registerBot(telegramClient);
        log.info("Telegram Bot registered succesfully");
    }
}
