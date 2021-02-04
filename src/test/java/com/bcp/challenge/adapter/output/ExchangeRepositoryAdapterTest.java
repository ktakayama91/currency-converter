package com.bcp.challenge.adapter.output;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.bcp.challenge.adapter.output.repository.ExchangeJpaRepository;
import com.bcp.challenge.adapter.output.repository.entity.Currency;
import com.bcp.challenge.adapter.output.repository.entity.Exchange;
import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.domain.ExchangeModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */

@ExtendWith(MockitoExtension.class)
class ExchangeRepositoryAdapterTest {

    @InjectMocks
    private ExchangeRepositoryAdapter exchangeRepositoryAdapter;

    @Mock
    private ExchangeJpaRepository exchangeJpaRepository;

    @Test
    public void findExchangeRateByCurrencyNameSuccessTest() {
        Currency currencyUSD = Currency
                .builder()
                .id(1L)
                .name("USD")
                .build();
        Currency currencyPEN = Currency
                .builder()
                .id(2L)
                .name("PEN")
                .build();
        LocalDateTime localDateTime = LocalDateTime.now();
        Exchange exchange1 = Exchange
                .builder()
                .id(1L)
                .currencyFrom(currencyUSD)
                .currencyTo(currencyPEN)
                .rate(3.62)
                .createdOn(localDateTime.minusDays(2L))
                .build();
        Exchange exchange2 = Exchange
                .builder()
                .id(1L)
                .currencyFrom(currencyUSD)
                .currencyTo(currencyPEN)
                .rate(3.65)
                .createdOn(localDateTime)
                .build();
        List<Exchange> exchangeList = Arrays.asList(exchange1, exchange2);
        Mockito.when(exchangeJpaRepository.findExchangeByCurrencyFromAndCurrencyTo(Mockito.any(), Mockito.any()))
                .thenReturn(exchangeList);

        CurrencyModel currencyModelPEN = CurrencyModel
                .builder()
                .id(1L)
                .name("PEN")
                .build();
        CurrencyModel currencyModelUSD = CurrencyModel
                .builder()
                .id(2L)
                .name("USD")
                .build();
        ExchangeModel exchangeModel = exchangeRepositoryAdapter.findExchangeRateByCurrencyName(currencyModelUSD,currencyModelPEN);

        assertNotNull(exchangeModel);
        assertEquals("USD", exchangeModel.getFrom().getName());
        assertEquals("PEN", exchangeModel.getTo().getName());
        assertEquals(3.65, exchangeModel.getRate());

    }

    @Test
    public void findExchangeRateByCurrencyName_whenEntityNotFoundException() {
        Mockito.when(exchangeJpaRepository.findExchangeByCurrencyFromAndCurrencyTo(Mockito.any(), Mockito.any()))
                .thenReturn(Arrays.asList());
        CurrencyModel currencyModel = CurrencyModel.builder().build();
        assertThrows(EntityNotFoundException.class,
                () -> exchangeRepositoryAdapter.findExchangeRateByCurrencyName(currencyModel, currencyModel));
    }

}