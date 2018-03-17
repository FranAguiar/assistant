package com.bot.config;

public class MessagesConf extends ConfigLoader {

    private static final String FILE_NAME = "messages.properties";
    private static MessagesConf messagesConf = null;

    private MessagesConf() {
        super(FILE_NAME);
    }

    public static MessagesConf getInstance() {
        if (messagesConf == null) {
            messagesConf = new MessagesConf();
        }
        return messagesConf;
    }

    public String getStartMessage() {
        return getProperty(
                "start_message",
                "Wellcome");
    }

    public String getCommandError() {
        return getProperty(
                "command_error",
                "That isn't a valid command");
    }

    public String getIpMsgError() {
        return getProperty(
                "ip_msg_error",
                "Something is not working here, please call a human");
    }

    public String getIpMsgOk() {
        return getProperty(
                "ip_msg_ok",
                "The current IP is: %s");
    }

    public String getHelpDesc() {
        return getProperty(
                "help_description",
                "Show list of available commands");
    }

    public String getIpDesc() {
        return getProperty(
                "ip_description",
                "Return the current IP of the server");
    }

    public String getAddToListDesc() {
        String tabulation = "\\\n\\\t\\\t\\\t\\\t\\\t\\\t\\\t\\\t\\\t\\\t\\\t\\\t";
        String defaultMsg = "List manager, use with the following options: " +
                "%s ADD this,is,a,list" +
                "%s QUIT this,list" +
                "%s SHOW" +
                "%s DEL";

        return getProperty(
                "add_to_list_description",
                defaultMsg).replaceAll("%s", tabulation);
    }

    public String getListDeleted() {
        return getProperty("list_deleted", "");
    }

    public String getListNotExist() {
        return getProperty("list_not_exist", "");
    }

    public String getListOptionNotValid() {
        return getProperty("list_option_not_valid", "");
    }
}
