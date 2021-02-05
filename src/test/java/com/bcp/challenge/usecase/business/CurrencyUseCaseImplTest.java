package com.bcp.challenge.usecase.business;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.domain.ExchangeModel;
import com.bcp.challenge.usecase.business.exceptions.CurrencyConverterException;
import com.bcp.challenge.usecase.business.response.CurrencyConverterResponse;
import com.bcp.challenge.usecase.business.response.CurrencyResponse;
import com.bcp.challenge.usecase.port.output.CurrencyRepositoryPort;
import com.bcp.challenge.usecase.port.output.ExchangeRepositoryPort;
import io.reactivex.Observable;
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
class CurrencyUseCaseImplTest {

    @InjectMocks
    private CurrencyUseCaseImpl currencyUseCase;

    @Mock
    private CurrencyRepositoryPort currencyRepositoryPort;

    @Mock
    private ExchangeRepositoryPort exchangeRepositoryPort;

    @Test
    public void findAllSuccessTest() {
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

        List<CurrencyModel> currencyModelList = Arrays.asList(currencyModelPEN, currencyModelUSD);
        Mockito.when(currencyRepositoryPort.findAll()).thenReturn(Observable.fromIterable(currencyModelList));

        Observable<CurrencyResponse> observable = currencyUseCase.findAll();

        TestObserver<CurrencyResponse> testObserver = new TestObserver<>();
        observable.subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(2);
    }

    @Test
    public void findAllEmptyTest() {
        List<CurrencyModel> currencyModelList = Arrays.asList();
        Mockito.when(currencyRepositoryPort.findAll()).thenReturn(Observable.fromIterable(currencyModelList));
        Observable<CurrencyResponse> observable = currencyUseCase.findAll();

        TestObserver<CurrencyResponse> testObserver = new TestObserver<>();
        observable.subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
    }

    @Test
    public void convertSuccessTest() {
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
                .rate(3.64)
                .build();

        Mockito.when(exchangeRepositoryPort.findExchangeRateByCurrencyName(currencyModelUSD,
                currencyModelPEN)).thenReturn(Single.just(exchangeModel));

        TestObserver<CurrencyConverterResponse> observer = new TestObserver();
        currencyUseCase.convert(100.00, currencyFrom, currencyTo)
                .subscribe(observer);


        observer.assertValueCount(1);
        observer.assertValue(currencyConverterResponse ->
                currencyConverterResponse.getCurrencyTo().equalsIgnoreCase("PEN"));
        observer.assertValue(currencyConverterResponse ->
                currencyConverterResponse.getCurrencyFrom().equalsIgnoreCase("USD"));
        observer.assertValue(currencyConverterResponse ->
                currencyConverterResponse.getCurrencyTo().equalsIgnoreCase("PEN"));
        observer.assertValue(currencyConverterResponse ->
                currencyConverterResponse.getRate() == 3.64);
    }

    @Test
    public void convertTest_whenCurrencyConverterException() {
        String currencyFrom = "USD";
        String currencyTo = "USD";

        assertThrows(CurrencyConverterException.class,
                () -> currencyUseCase.convert(100.00, currencyFrom, currencyTo));
    }
}