package com.bcp.challenge.usecase.port.output;

import java.util.List;

import com.bcp.challenge.domain.CurrencyModel;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */

public interface CurrencyRepositoryPort {
    List<CurrencyModel> findAll();
    CurrencyModel findCurrencyByName(String currencyName);
}
