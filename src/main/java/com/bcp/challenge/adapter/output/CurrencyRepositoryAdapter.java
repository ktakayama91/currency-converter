package com.bcp.challenge.adapter.output;

import java.util.List;
import com.bcp.challenge.adapter.output.repository.CurrencyJpaRepository;
import com.bcp.challenge.adapter.output.repository.entity.Currency;
import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.usecase.business.exceptions.CurrencyNotFoundException;
import com.bcp.challenge.usecase.port.output.CurrencyRepositoryPort;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.Builder;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */

@Builder
public class CurrencyRepositoryAdapter implements CurrencyRepositoryPort {

    private final CurrencyJpaRepository currencyJpaRepository;

    @Override
    public Observable<CurrencyModel> findAll() {
        List<Currency> currencyList = currencyJpaRepository.findAll();
        return Observable.fromIterable(currencyList)
                .map(currency -> CurrencyModel.builder()
                        .id(currency.getId())
                        .name(currency.getName())
                        .build());
    }

    @Override
    public Single<CurrencyModel> findCurrencyByName(String currencyName) {
        return Single.create(singleEmitter -> {
            Currency currency = currencyJpaRepository.findCurrencyByName(currencyName);
            if (currency != null) {
                singleEmitter.onSuccess(this.toCurrencyModel(currency));
            } else {
                singleEmitter.onError(new CurrencyNotFoundException("Currency not found"));
            }
        });
    }

    private CurrencyModel toCurrencyModel(Currency currency) {
        return CurrencyModel
                .builder()
                .id(currency.getId())
                .name(currency.getName())
                .build();
    }
}
