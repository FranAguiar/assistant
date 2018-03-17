package com.bot.components;

import com.bot.commons.Messages;
import com.bot.commons.Utils;
import com.bot.services.MyIpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScheduledTasks implements Runnable {

    private static final Logger log = LogManager.getLogger(ScheduledTasks.class);

    private static TelegramClient telegramClient;
    private TaskCheck taskCheck;
    private Messages messages;
    private MyIpService myIpService;
    private String myIp = "0.0.0.0";

    public ScheduledTasks() {
        taskCheck = new TaskCheck();
        telegramClient = TelegramClient.getInstance();
        messages = Messages.getInstance();
        myIpService = MyIpService.getInstance();
        startMessage();
    }

    @Override
    public void run() {
        while (true) {
            if (taskCheck.isTimeToCheckMyIpAddress()) {
                checkMyIpAddress();
            }
            Utils.wait(60);
        }
    }

    private void startMessage() {
        log.info("Scheduler started successfully");
        telegramClient.sendMessage(messages.start());
    }

    private void checkMyIpAddress() {
        log.info("Checking IP address");
        String currentIp = myIpService.lookup();
        if (!myIp.equals(currentIp)) {
            telegramClient.sendMessage(messages.serverIp(currentIp));
            myIp = currentIp;
        }
    }
}
