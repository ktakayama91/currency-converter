package com.bcp.challenge.adapter.output;

import java.util.List;
import java.util.stream.Collectors;
import com.bcp.challenge.adapter.output.repository.CurrencyJpaRepository;
import com.bcp.challenge.adapter.output.repository.entity.Currency;
import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.usecase.port.output.CurrencyRepositoryPort;
import lombok.Builder;

/**
 * @author Kei Takayama
 * Created on 2/02/21.
 */

@Builder
public class CurrencyRepositoryAdapter implements CurrencyRepositoryPort {

    private final CurrencyJpaRepository currencyJpaRepository;

    @Override
    public List<CurrencyModel> findAll() {
        List<Currency> currencyList = currencyJpaRepository.findAll();
        List<CurrencyModel> currencyModelList = currencyList.stream()
                .map(currency ->
                        CurrencyModel.builder()
                        .id(currency.getId())
                        .name(currency.getName())
                        .build())
                .collect(Collectors.toList());
        return currencyModelList;
    }

    @Override
    public CurrencyModel findCurrencyByName(String currencyName) {
        return toCurrencyModel(currencyJpaRepository.findCurrencyByName(currencyName));
    }

    private CurrencyModel toCurrencyModel(Currency currency) {
        return CurrencyModel
                .builder()
                .id(currency.getId())
                .name(currency.getName())
                .build();
    }
}
