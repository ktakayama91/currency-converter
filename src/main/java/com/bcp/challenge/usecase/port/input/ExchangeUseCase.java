package com.bcp.challenge.usecase.port.input;

import com.bcp.challenge.usecase.business.response.ExchangeResponse;
import io.reactivex.Single;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
public interface ExchangeUseCase {
    Single<ExchangeResponse> updateExchange(Double rate, String currencyFrom, String currencyTo);
}
