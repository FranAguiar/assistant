package com.bot.config;

public class BotConf extends ConfigLoader {

    private static final String FILE_NAME = "config.properties";
    private static BotConf botConf = null;

    private BotConf() {
        super(FILE_NAME);
    }

    public static BotConf getInstance() {
        if (botConf == null) {
            botConf = new BotConf();
        }
        return botConf;
    }

    public String getBotUsername() {
        return getProperty("bot_user_name");
    }

    public String getBotToken() {
        return getProperty("bot_token");
    }

    public long getChatId() {
        return Long.parseLong(getProperty("bot_chat_id"));
    }

    public int getCheckIpRange() {
        return getProperty("check_ip_range", 5);
    }
}
