package org.maveric.currencyexchange.service;

import org.maveric.currencyexchange.payload.ExchangeRates;

public interface IExchangeRatesService {
    ExchangeRates getLatestExchangeRates(String baseCurrency);
}
