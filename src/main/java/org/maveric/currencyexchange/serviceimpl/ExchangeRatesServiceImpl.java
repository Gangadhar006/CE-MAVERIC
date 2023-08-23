package org.maveric.currencyexchange.serviceimpl;

import org.maveric.currencyexchange.payload.ExchangeRates;
import org.maveric.currencyexchange.service.IExchangeRatesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRatesServiceImpl implements IExchangeRatesService {

    private String API_URL;

    public ExchangeRatesServiceImpl(@Value("${exchange-rates.api-url}") String API_URL) {
        this.API_URL = API_URL;
    }

    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeRates getLatestExchangeRates(String baseCurrency) {
        return restTemplate.getForObject(API_URL.concat(baseCurrency), ExchangeRates.class);
    }
}