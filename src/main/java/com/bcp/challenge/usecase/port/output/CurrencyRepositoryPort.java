package com.bcp.challenge.usecase.port.output;

import com.bcp.challenge.domain.CurrencyModel;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */

public interface CurrencyRepositoryPort {
    Observable<CurrencyModel> findAll();
    Single<CurrencyModel> findCurrencyByName(String currencyName);
}
