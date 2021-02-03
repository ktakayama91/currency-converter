package com.bcp.challenge.usecase.port.input;

import com.bcp.challenge.usecase.business.response.CurrencyConverterResponse;
import com.bcp.challenge.usecase.business.response.CurrencyResponse;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
public interface CurrencyUseCase {
    Observable<CurrencyResponse> findAll();
    Single<CurrencyConverterResponse> convert(Double amount, String currencyNameFrom, String currencyNameTo);
}
