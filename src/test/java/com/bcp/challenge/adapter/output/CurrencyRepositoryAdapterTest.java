package com.bcp.challenge.adapter.output;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;

import com.bcp.challenge.adapter.output.repository.CurrencyJpaRepository;
import com.bcp.challenge.adapter.output.repository.entity.Currency;
import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.usecase.business.exceptions.CurrencyNotFoundException;
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
class CurrencyRepositoryAdapterTest {

    @InjectMocks
    private CurrencyRepositoryAdapter currencyRepositoryAdapter;

    @Mock
    private CurrencyJpaRepository currencyJpaRepository;

    @Test
    public void findAllSuccessTest() {
        Currency currency = Currency
                .builder()
                .id(1L)
                .name("PEN")
                .build();
        List<Currency> currencyList = Arrays.asList(currency);

        Mockito.when(currencyJpaRepository.findAll())
                .thenReturn(currencyList);

        List<CurrencyModel> currencyModelList = currencyRepositoryAdapter.findAll();

        assertEquals(1, currencyModelList.size());
        assertEquals("PEN", currencyModelList.get(0).getName());
    }

    @Test
    public void findCurrencyByNameSuccessTest() {
        Currency currency = Currency
                .builder()
                .id(1L)
                .name("PEN")
                .build();
        Mockito.when(currencyJpaRepository.findCurrencyByName(Mockito.anyString()))
                .thenReturn(currency);

        CurrencyModel currencyModel = currencyRepositoryAdapter.findCurrencyByName("PEN");

        assertNotNull(currencyModel);
        assertEquals("PEN", currencyModel.getName());
    }

    @Test
    public void findCurrencyTest_whenCurrencyNotFoundException() {
        Mockito.when(currencyJpaRepository.findCurrencyByName(Mockito.anyString()))
                .thenReturn(null);

        assertThrows(CurrencyNotFoundException.class,
                () -> currencyRepositoryAdapter.findCurrencyByName("YEN"));
    }

}