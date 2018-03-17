package com.bot.commons;

import com.bot.commons.constants.Commands;
import com.bot.config.MessagesConf;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Messages {

    private static final Logger log = LogManager.getLogger(Messages.class);

    private static Messages messages = null;
    private static MessagesConf messagesConf = null;

    public static Messages getInstance() {
        if (messages == null) {
            messages = new Messages();
        }

        if (messagesConf == null) {
            messagesConf = MessagesConf.getInstance();
        }
        return messages;
    }

    public String help() {
        StringBuilder msg = new StringBuilder();
        for (Commands command : Commands.values()) {
            msg.append(command.getCommand());
            msg.append(" - ");
            msg.append(command.getDescription());
            msg.append("\n");
        }
        return msg.toString();
    }

    public String commandError() {
        return messagesConf.getCommandError();
    }

    public String serverIp(String currentIp) {
        log.info(String.format("Ip service response: [ %s ]", currentIp));
        if (StringUtils.isBlank(currentIp)) {
            return messagesConf.getIpMsgError();
        } else {
            return String.format(messagesConf.getIpMsgOk(), currentIp);
        }
    }

    public String helpDesc() {
        return messagesConf.getHelpDesc();
    }

    public String ipDesc() {
        return messagesConf.getIpDesc();
    }

    public String addToListDesc() {
        return messagesConf.getAddToListDesc();
    }

    public String start() {
        return String.format(messagesConf.getStartMessage(), Commands.HELP.getCommand());
    }

    public String listDeleted() {
        return messagesConf.getListDeleted();
    }

    public String listNotExist() {
        return messagesConf.getListNotExist();
    }

    public String listOptionNotValid(String option) {
        return String.format(
                messagesConf.getListOptionNotValid(),
                option);
    }
}
