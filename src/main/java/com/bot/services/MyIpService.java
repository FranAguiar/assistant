package com.bot.services;


import com.bot.components.RestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.bot.commons.constants.ServicesUrls.WHAT_IS_MY_FUCKING_IP_URL;
import static org.springframework.http.MediaType.TEXT_PLAIN;

public class MyIpService {
    private static final Logger log = LogManager.getLogger(MyIpService.class);

    private static MyIpService myIpService = null;
    private static RestClient restClient = null;

    public static MyIpService getInstance() {
        if (myIpService == null) {
            myIpService = new MyIpService();
        }

        if (restClient == null) {
            restClient = RestClient.getInstance();
        }
        return myIpService;
    }


    public String lookup() {
        String response;
        try {
            response = restClient.httpGetAsString(
                    restClient.getHeaders(TEXT_PLAIN),
                    WHAT_IS_MY_FUCKING_IP_URL);
        } catch (Exception e) {
            response = "";
            log.error("Cannot get current IP", e);
        }
        return response;
    }
}
