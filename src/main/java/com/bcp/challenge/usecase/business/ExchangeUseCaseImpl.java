package com.bcp.challenge.usecase.business;

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
        if (currencyNameFrom.equalsIgnoreCase(currencyNameTo)) {
            throw new CurrencyConverterException("Both currencies are equals.");
        } else {
            return currencyRepositoryPort.findCurrencyByName(currencyNameFrom)
                    .flatMap(currencyModelFrom -> currencyRepositoryPort.findCurrencyByName(currencyNameTo)
                            .flatMap(currencyModelTo -> exchangeRepositoryPort.addExchangeRate(currencyModelFrom, currencyModelTo, rate)))
                    .map(exchangeModel -> ExchangeResponse
                            .builder()
                            .currencyFrom(exchangeModel.getFrom().getName())
                            .currencyTo(exchangeModel.getTo().getName())
                            .rate(exchangeModel.getRate())
                            .build());
        }
    }
}
