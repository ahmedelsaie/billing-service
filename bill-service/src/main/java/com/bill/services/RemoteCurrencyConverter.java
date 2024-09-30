package com.bill.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Currency;

@Service
public class RemoteCurrencyConverter {

    private final RestTemplate restTemplate;

    private final String apiKey;

    public RemoteCurrencyConverter(RestTemplate restTemplate, @Value("${API_KEY}")
    String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    @Cacheable(value = "currencyRates")
    public BigDecimal getConvertRateFromUSD(Currency currency) {
        System.out.println("inside hit here");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String url = "https://openexchangerates.org/api/latest.json";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.
                fromHttpUrl(url);
        uriComponentsBuilder.queryParam("app_id", apiKey);
        uriComponentsBuilder.queryParam("symbols", currency.getCurrencyCode());

        ResponseEntity<JsonNode> responseEntity =
                restTemplate.exchange(uriComponentsBuilder.toUriString(),
                        HttpMethod.GET, entity, JsonNode.class);

        double rate = responseEntity.getBody().get("rates").get(currency.getCurrencyCode()).asDouble();
        return BigDecimal.valueOf(rate);
    }
}
