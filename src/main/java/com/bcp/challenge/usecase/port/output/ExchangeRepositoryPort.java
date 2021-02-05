package com.bcp.challenge.usecase.port.output;

import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.domain.ExchangeModel;
import io.reactivex.Single;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
public interface ExchangeRepositoryPort {
    Single<ExchangeModel> addExchangeRate(CurrencyModel currencyFrom, CurrencyModel currencyTo, Double rate);
    Single<ExchangeModel> findExchangeRateByCurrencyName(CurrencyModel currencyFrom, CurrencyModel currencyTo);
}
