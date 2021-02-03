package com.bcp.challenge.adapter.output.repository;

import com.bcp.challenge.adapter.output.repository.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
public interface CurrencyJpaRepository extends JpaRepository<Currency, Long> {
    Currency findCurrencyByName(String name);
}
