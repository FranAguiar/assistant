package com.bot.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class ConfigLoader {
    private static final Logger log = LogManager.getLogger(ConfigLoader.class);

    private Properties config;

    ConfigLoader(String fileName) {
        try {
            File configFile = new File(fileName);
            config = new Properties();
            config.load(new FileInputStream(configFile));
            log.info(String.format("Config from %s load successfully", fileName));
        } catch (IOException e) {
            log.error(String.format("Couldn't load config from file %S", fileName), e);
        }
    }

    String getProperty(String key, String defaultIfNull) {
        String property = config.getProperty(key);
        return property == null ? defaultIfNull : property;
    }

    String getProperty(String key) {
        return config.getProperty(key);
    }

    int getProperty(String key, int defaultIfNull) {
        String property = config.getProperty(key);
        return Integer.parseInt(
                property == null ? String.valueOf(defaultIfNull) : property);
    }
}
