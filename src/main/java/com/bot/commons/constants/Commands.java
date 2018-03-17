package com.bot.commons.constants;

import com.bot.commons.Messages;

public enum Commands {

    HELP("/help", Messages.getInstance().helpDesc()),
    SERVER_IP("/ip", Messages.getInstance().ipDesc()),
    ADD_LIST("/list", Messages.getInstance().addToListDesc());

    private final String command;
    private final String description;

    Commands(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }
}
