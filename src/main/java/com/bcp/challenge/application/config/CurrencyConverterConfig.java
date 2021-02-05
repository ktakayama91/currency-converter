package com.bcp.challenge.application.config;

import com.bcp.challenge.adapter.output.CurrencyRepositoryAdapter;
import com.bcp.challenge.adapter.output.ExchangeRepositoryAdapter;
import com.bcp.challenge.adapter.output.repository.CurrencyJpaRepository;
import com.bcp.challenge.adapter.output.repository.ExchangeJpaRepository;
import com.bcp.challenge.usecase.business.CurrencyUseCaseImpl;
import com.bcp.challenge.usecase.business.ExchangeUseCaseImpl;
import com.bcp.challenge.usecase.port.input.CurrencyUseCase;
import com.bcp.challenge.usecase.port.input.ExchangeUseCase;
import com.bcp.challenge.usecase.port.output.CurrencyRepositoryPort;
import com.bcp.challenge.usecase.port.output.ExchangeRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */

@Configuration
@ComponentScan(basePackages = "com.bcp.challenge.adapter")
public class CurrencyConverterConfig {

    @Bean
    public ExchangeRepositoryPort getExchangeRepositoryPort(ExchangeJpaRepository exchangeJpaRepository) {
        return ExchangeRepositoryAdapter
                .builder()
                .exchangeJpaRepository(exchangeJpaRepository)
                .build();
    }

    @Bean
    public CurrencyRepositoryPort getCurrencyRepositoryPort(CurrencyJpaRepository currencyJpaRepository) {
        return CurrencyRepositoryAdapter
                .builder()
                .currencyJpaRepository(currencyJpaRepository)
                .build();

    }

    @Bean
    public CurrencyUseCase getCurrencyUseCase(ExchangeRepositoryPort exchangeRepositoryPort,
                                              CurrencyRepositoryPort currencyRepositoryPort) {
        return CurrencyUseCaseImpl
                .builder()
                .exchangeRepositoryPort(exchangeRepositoryPort)
                .currencyRepositoryPort(currencyRepositoryPort)
                .build();
    }

    @Bean
    public ExchangeUseCase getExchangeUseCase(ExchangeRepositoryPort exchangeRepositoryPort,
                                              CurrencyRepositoryPort currencyRepositoryPort) {
        return ExchangeUseCaseImpl
                .builder()
                .exchangeRepositoryPort(exchangeRepositoryPort)
                .currencyRepositoryPort(currencyRepositoryPort)
                .build();
    }
}
