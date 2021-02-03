package com.bcp.challenge.usecase.business;

import java.util.List;
import java.util.stream.Collectors;

import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.domain.ExchangeModel;
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
        List<CurrencyModel> currencyModelList = currencyRepositoryPort.findAll();
        List<CurrencyResponse> currencyResponseList = currencyModelList.stream()
                .map(currencyModel -> CurrencyResponse
                        .builder()
                        .name(currencyModel.getName())
                        .build())
                .collect(Collectors.toList());
        return Observable.fromIterable(currencyResponseList);
    }

    @Override
    public Single<CurrencyConverterResponse> convert(Double amount, String currencyNameFrom, String currencyNameTo) {
        CurrencyModel currencyFrom = currencyRepositoryPort.findCurrencyByName(currencyNameFrom);
        CurrencyModel currencyTo = currencyRepositoryPort.findCurrencyByName(currencyNameTo);

        ExchangeModel exchangeModel = exchangeRepositoryPort.findExchangeRateByCurrencyName(currencyFrom, currencyTo);

        return Single.create(singleEmitter -> {
            CurrencyConverterResponse currencyConverterResponse =
                    CurrencyConverterResponse
                    .builder()
                    .amount(amount)
                    .exchangeAmount(amount * exchangeModel.getRate())
                    .currencyFrom(currencyFrom.getName())
                    .currencyTo(currencyTo.getName())
                    .rate(exchangeModel.getRate())
                    .build();

            singleEmitter.onSuccess(currencyConverterResponse);
        });
    }
}
