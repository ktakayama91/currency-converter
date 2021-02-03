package com.bcp.challenge.usecase.business;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import com.bcp.challenge.domain.CurrencyModel;
import com.bcp.challenge.usecase.business.response.CurrencyResponse;
import com.bcp.challenge.usecase.port.output.CurrencyRepositoryPort;
import io.reactivex.Observable;
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
        Mockito.when(currencyRepositoryPort.findAll()).thenReturn(currencyModelList);

        Observable<CurrencyResponse> observable = currencyUseCase.findAll();

        TestObserver<CurrencyResponse> testObserver = new TestObserver<>();
        observable.subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(2);
    }



}