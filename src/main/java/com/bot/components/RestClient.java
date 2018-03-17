package com.bot.components;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestClient {

    private static RestTemplate restTemplate = null;
    private static RestClient restClient = null;

    public static RestClient getInstance() {

        if (restClient == null) {
            restClient = new RestClient();
        }

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restClient;
    }

    public HttpHeaders getHeaders(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return headers;
    }

    public String httpGetAsString(HttpHeaders headers, String url) {
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                String.class);
        return responseEntity.getBody();
    }
}
