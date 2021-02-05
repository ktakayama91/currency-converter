package com.bcp.challenge.adapter.output;

import java.time.LocalDateTime;
import java.util.List;

import com.bcp.challenge.adapter.output.repository.ExchangeJpaRepository;
import com.bcp.challenge.adapter.output.repository.entity.Currency;
import com.bcp.challenge.adapter.output.repository.entity.Exchange;
import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.domain.ExchangeModel;
import com.bcp.challenge.usecase.business.exceptions.ExchangeNotFoundException;
import com.bcp.challenge.usecase.port.output.ExchangeRepositoryPort;
import io.reactivex.Single;
import lombok.Builder;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
@Builder
public class ExchangeRepositoryAdapter implements ExchangeRepositoryPort {

    private final ExchangeJpaRepository exchangeJpaRepository;

    @Override
    public Single<ExchangeModel> addExchangeRate(CurrencyModel currencyFrom, CurrencyModel currencyTo, Double rate) {
        return Single.create(singleEmitter -> {
            Exchange exchange = Exchange
                    .builder()
                    .currencyFrom(toCurrency(currencyFrom))
                    .currencyTo(toCurrency(currencyTo))
                    .rate(rate)
                    .createdOn(LocalDateTime.now())
                    .build();
            singleEmitter.onSuccess(toExchangeModel(exchangeJpaRepository.save(exchange)));
        });
    }

    @Override
    public Single<ExchangeModel> findExchangeRateByCurrencyName(CurrencyModel currencyFrom, CurrencyModel currencyTo) {
        return Single.create(singleEmitter -> {
            List<Exchange> exchanges = exchangeJpaRepository.findExchangeByCurrencyFromAndCurrencyTo(
                    toCurrency(currencyFrom),
                    toCurrency(currencyTo));

            if (exchanges.isEmpty()) {
                singleEmitter.onError(new ExchangeNotFoundException("Exchange Rate not found"));
            } else {
                Exchange exchange = exchanges.stream()
                        .sorted((e1, e2) -> e2.getCreatedOn().compareTo(e1.getCreatedOn()))
                        .findFirst()
                        .get();
                singleEmitter.onSuccess(this.toExchangeModel(exchange));
            }
        });
    }

    private Currency toCurrency(CurrencyModel currencyModel) {
        return Currency
                .builder()
                .id(currencyModel.getId())
                .name(currencyModel.getName())
                .build();
    }

    private ExchangeModel toExchangeModel(Exchange exchange) {
        return ExchangeModel
                .builder()
                .from(toCurrencyModel(exchange.getCurrencyFrom()))
                .to(toCurrencyModel(exchange.getCurrencyTo()))
                .rate(exchange.getRate())
                .build();

    }

    private CurrencyModel toCurrencyModel(Currency currency) {
        return CurrencyModel
                .builder()
                .id(currency.getId())
                .name(currency.getName())
                .build();
    }
}
