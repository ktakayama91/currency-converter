package com.bcp.challenge.adapter.output;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import com.bcp.challenge.adapter.output.repository.CurrencyJpaRepository;
import com.bcp.challenge.adapter.output.repository.entity.Currency;
import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.usecase.business.exceptions.CurrencyNotFoundException;
import io.reactivex.observers.TestObserver;
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

        TestObserver<CurrencyModel> testObserver = new TestObserver<>();
        currencyRepositoryAdapter.findAll().subscribe(testObserver);

        testObserver.assertValue(currencyModel ->
                currencyModel.getId() == 1);
        testObserver.assertValue(currencyModel ->
                currencyModel.getName().equalsIgnoreCase("PEN"));
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

        TestObserver<CurrencyModel> testObserver = new TestObserver();

       currencyRepositoryAdapter.findCurrencyByName("PEN")
               .subscribe(testObserver);

       testObserver.assertValue(currencyModel ->
               currencyModel.getName().equalsIgnoreCase("PEN"));
    }

    @Test
    public void findCurrencyTest_whenCurrencyNotFoundException() {
        Mockito.when(currencyJpaRepository.findCurrencyByName(Mockito.anyString()))
                .thenReturn(null);

        TestObserver<CurrencyModel> testObserver = new TestObserver();
        currencyRepositoryAdapter.findCurrencyByName("YEN")
                .subscribe(testObserver);

        testObserver.assertError(CurrencyNotFoundException.class);
        testObserver.assertErrorMessage("Currency not found");
    }

}