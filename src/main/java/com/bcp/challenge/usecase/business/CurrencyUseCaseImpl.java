package com.bcp.challenge.usecase.business;

import com.bcp.challenge.usecase.business.exceptions.CurrencyConverterException;
import com.bcp.challenge.usecase.business.response.CurrencyConverterResponse;
import com.bcp.challenge.usecase.business.response.CurrencyResponse;
import com.bcp.challenge.usecase.port.input.CurrencyUseCase;
import com.bcp.challenge.usecase.port.output.CurrencyRepositoryPort;
import com.bcp.challenge.usecase.port.output.ExchangeRepositoryPort;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.Builder;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
@Builder
public class CurrencyUseCaseImpl implements CurrencyUseCase {

    private final ExchangeRepositoryPort exchangeRepositoryPort;
    private final CurrencyRepositoryPort currencyRepositoryPort;

    @Override
    public Observable<CurrencyResponse> findAll() {
      return currencyRepositoryPort.findAll()
              .map(currencyModel -> CurrencyResponse
                      .builder()
                      .name(currencyModel.getName())
                      .build());
    }

    @Override
    public Single<CurrencyConverterResponse> convert(Double amount, String currencyNameFrom, String currencyNameTo) {
        if (currencyNameFrom.equalsIgnoreCase(currencyNameTo)) {
            throw new CurrencyConverterException("Both currencies are equals.");
        } else {
            return currencyRepositoryPort.findCurrencyByName(currencyNameFrom)
                    .flatMap(currencyModelFrom -> currencyRepositoryPort.findCurrencyByName(currencyNameTo)
                            .flatMap(currencyModelTo -> exchangeRepositoryPort.findExchangeRateByCurrencyName(currencyModelFrom, currencyModelTo)))
                    .map(exchangeModel -> CurrencyConverterResponse
                            .builder()
                            .amount(amount)
                            .exchangeAmount(amount * exchangeModel.getRate())
                            .currencyFrom(exchangeModel.getFrom().getName())
                            .currencyTo(exchangeModel.getTo().getName())
                            .rate(exchangeModel.getRate())
                            .build());

        }
    }
}