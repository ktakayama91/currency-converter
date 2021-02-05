package com.bcp.challenge.usecase.business;

import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.domain.ExchangeModel;
import com.bcp.challenge.usecase.business.exceptions.CurrencyConverterException;
import com.bcp.challenge.usecase.business.response.ExchangeResponse;
import com.bcp.challenge.usecase.port.input.ExchangeUseCase;
import com.bcp.challenge.usecase.port.output.CurrencyRepositoryPort;
import com.bcp.challenge.usecase.port.output.ExchangeRepositoryPort;
import io.reactivex.Single;
import lombok.Builder;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */

@Builder
public class ExchangeUseCaseImpl implements ExchangeUseCase {

    private final ExchangeRepositoryPort exchangeRepositoryPort;
    private final CurrencyRepositoryPort currencyRepositoryPort;

    @Override
    public Single<ExchangeResponse> updateExchange(Double rate, String currencyNameFrom, String currencyNameTo) {
        return Single.create(singleEmitter -> {
            if (currencyNameFrom.equalsIgnoreCase(currencyNameTo)) {
                singleEmitter.onError(new CurrencyConverterException("Both currencies are equals."));
            } else {
                CurrencyModel currencyFrom = currencyRepositoryPort.findCurrencyByName(currencyNameFrom);
                CurrencyModel currencyTo = currencyRepositoryPort.findCurrencyByName(currencyNameTo);

                ExchangeModel exchangeModel = exchangeRepositoryPort.addExchangeRate(currencyFrom, currencyTo, rate);

                ExchangeResponse exchangeResponse =
                        ExchangeResponse
                                .builder()
                                .currencyFrom(exchangeModel.getFrom().getName())
                                .currencyTo(exchangeModel.getTo().getName())
                                .rate(exchangeModel.getRate())
                                .build();
                singleEmitter.onSuccess(exchangeResponse);
            }
        });
    }
}
