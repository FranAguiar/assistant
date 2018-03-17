package com.bot.components;

import com.bot.commons.Utils;
import com.bot.config.BotConf;

public class TaskCheck {

    private int checkIpRange;
    private int ipNexTimeCheck;

    TaskCheck() {
        checkIpRange = BotConf.getInstance().getCheckIpRange();
        String currentMinutes = Utils.getCurrentMinutes();
        ipNexTimeCheck = Integer.parseInt(currentMinutes);
    }


    protected boolean isTimeToCheckMyIpAddress() {
        int currentMinutes = Integer.parseInt(Utils.getCurrentMinutes());
        if ((currentMinutes == ipNexTimeCheck) && checkIpRange !=0) {
            ipNexTimeCheck = ipNexTimeCheck + checkIpRange;
            return true;
        } else {
            return false;
        }

    }
}
