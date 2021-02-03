package com.bcp.challenge.usecase.port.output;

import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.domain.ExchangeModel;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
public interface ExchangeRepositoryPort {
    ExchangeModel addExchangeRate(CurrencyModel currencyFrom, CurrencyModel currencyTo, Double rate);
    ExchangeModel findExchangeRateByCurrencyName(CurrencyModel currencyFrom, CurrencyModel currencyTo);
}
