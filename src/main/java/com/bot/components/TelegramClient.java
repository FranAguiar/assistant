package com.bot.components;

import com.bot.BotStarter;
import com.bot.commons.Messages;
import com.bot.commons.Utils;
import com.bot.config.BotConf;
import com.bot.services.ListService;
import com.bot.services.MyIpService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static com.bot.commons.constants.Commands.*;


public final class TelegramClient extends TelegramLongPollingBot {

    private static final Logger log = LogManager.getLogger(BotStarter.class);
    private static TelegramClient telegramClient = null;
    private String botName;
    private String botToken;
    private long chatId;

    private TelegramClient() {
        BotConf conf = BotConf.getInstance();
        botName = conf.getBotUsername();
        botToken = conf.getBotToken();
        chatId = conf.getChatId();
    }

    public static TelegramClient getInstance() {
        if (telegramClient == null) {
            telegramClient = new TelegramClient();
        }
        return telegramClient;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && message.isCommand()) {
                String message_text = message.getText();
                processCommand(message_text);
            }
        }
    }

    private void processCommand(String message_text) {
        try {
            log.info("Processing command");
            String command = message_text.split(" ")[0];
            Messages messages = Messages.getInstance();
            String textMsg;
            if (HELP.getCommand().equals(command)) {
                textMsg = messages.help();
            } else if (SERVER_IP.getCommand().equals(command)) {
                String currentIP = MyIpService.getInstance().lookup();
                textMsg = messages.serverIp(currentIP);
            } else if (ADD_LIST.getCommand().equals(command)) {
                String listCommand = Utils.removeFirstWord(message_text, " ");
                textMsg = ListService.getInstance().processListRequest(listCommand);
            } else {
                textMsg = messages.commandError();
            }
            sendMessage(textMsg);
            log.info("Command processed");
        } catch (Exception e) {
            log.error("Command not processed", e);
        }

    }

    public void sendMessage(String message_text) {
        if (StringUtils.isNotBlank(message_text)) {
            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(message_text);
            try {
                execute(message);
                log.info("Message sent successfully");
            } catch (TelegramApiException e) {
                log.error("Cannot send the message", e);
            }
        }

    }
}
