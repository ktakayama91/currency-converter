package com.bcp.challenge.usecase.business;

import static org.junit.jupiter.api.Assertions.*;

import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.domain.ExchangeModel;
import com.bcp.challenge.usecase.business.exceptions.CurrencyConverterException;
import com.bcp.challenge.usecase.business.response.ExchangeResponse;
import com.bcp.challenge.usecase.port.output.CurrencyRepositoryPort;
import com.bcp.challenge.usecase.port.output.ExchangeRepositoryPort;
import io.reactivex.Single;
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
class ExchangeUseCaseImplTest {

    @InjectMocks
    public ExchangeUseCaseImpl exchangeUseCase;

    @Mock
    private ExchangeRepositoryPort exchangeRepositoryPort;

    @Mock
    private CurrencyRepositoryPort currencyRepositoryPort;

    @Test
    public void updateExchangeSuccessTest() {
        String currencyFrom = "USD";
        String currencyTo = "PEN";

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

        Mockito.when(currencyRepositoryPort.findCurrencyByName(currencyFrom))
                .thenReturn(Single.just(currencyModelUSD));
        Mockito.when(currencyRepositoryPort.findCurrencyByName(currencyTo))
                .thenReturn(Single.just(currencyModelPEN));

        ExchangeModel exchangeModel = ExchangeModel
                .builder()
                .from(currencyModelUSD)
                .to(currencyModelPEN)
                .rate(1.00)
                .build();

        Mockito.when(exchangeRepositoryPort.addExchangeRate(Mockito.any(),Mockito.any(),Mockito.anyDouble()))
                .thenReturn(Single.just(exchangeModel));

        TestObserver<ExchangeResponse> observer = new TestObserver();
        exchangeUseCase.updateExchange(1.00, currencyFrom, currencyTo)
                .subscribe(observer);

        observer.assertValue(exchangeResponse ->
                exchangeResponse.getRate() == 1.00);
        observer.assertValue(exchangeResponse ->
                exchangeResponse.getCurrencyFrom().equalsIgnoreCase("USD"));
        observer.assertValue(exchangeResponse ->
                exchangeResponse.getCurrencyTo().equalsIgnoreCase("PEN"));
    }

    @Test
    public void updateExchangeTest_whenCurrencyConverterException() {
        String currencyFrom = "USD";
        String currencyTo = "USD";

        assertThrows(CurrencyConverterException.class,
                () -> exchangeUseCase.updateExchange(1.00, currencyFrom, currencyTo));
    }

}