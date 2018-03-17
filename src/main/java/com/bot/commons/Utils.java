package com.bot.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.util.StringJoiner;

public class Utils {
    private static final Logger log = LogManager.getLogger(Utils.class);

    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error("Can't wait", e);
        }
    }

    public static String getCurrentDateTime() {
        DateTime dateTime = new DateTime();
        return String.format("%s:%s", dateTime.getHourOfDay(), dateTime.getMinuteOfHour());
    }

    public static String getCurrentMinutes() {
        DateTime dateTime = new DateTime();
        return String.format("%s", dateTime.getMinuteOfHour());
    }


    public static String removeFirstWord(String text, String splitterChar) {
        StringJoiner result = new StringJoiner(splitterChar);
        String[] textArray = text.split(splitterChar);
        for (int index = 1; index < textArray.length; index++) {
            result.add(textArray[index]);
        }
        return result.toString();
    }
}
