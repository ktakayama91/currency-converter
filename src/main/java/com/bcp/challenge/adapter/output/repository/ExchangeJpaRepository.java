package com.bcp.challenge.adapter.output.repository;

import java.util.List;

import com.bcp.challenge.adapter.output.repository.entity.Currency;
import com.bcp.challenge.adapter.output.repository.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kei Takayama
 * Created on 2/02/21.
 */
public interface ExchangeJpaRepository extends JpaRepository<Exchange, Long> {
    List<Exchange> findExchangeByCurrencyFromAndCurrencyTo(Currency currencyFrom, Currency currencyTo);
}
