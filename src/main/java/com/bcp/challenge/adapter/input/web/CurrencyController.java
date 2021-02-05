package com.bcp.challenge.adapter.input.web;

import com.bcp.challenge.adapter.input.web.request.CurrencyConverterWebRequest;
import com.bcp.challenge.adapter.input.web.response.CurrencyConverterWebResponse;
import com.bcp.challenge.adapter.input.web.response.CurrencyWebResponse;
import com.bcp.challenge.usecase.port.input.CurrencyUseCase;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */

@Slf4j
@RestController
@RequestMapping("${application.currency.url}")
public class CurrencyController {

    private final CurrencyUseCase currencyUseCase;

    public CurrencyController(CurrencyUseCase currencyUseCase) {
        this.currencyUseCase = currencyUseCase;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Observable<ResponseEntity<CurrencyWebResponse>> findAll(){
        log.info("Init CurrencyController.findAll");
        return currencyUseCase.findAll()
                .map(currencyResponse -> CurrencyWebResponse
                        .builder()
                        .name(currencyResponse.getName())
                        .build())
                .map(currencyWebResponse ->
                        ResponseEntity.ok(currencyWebResponse))
                .subscribeOn(Schedulers.io())
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public Single<ResponseEntity<CurrencyConverterWebResponse>> convert(@RequestBody CurrencyConverterWebRequest currencyConverterRequest) {
        log.info("Init CurrencyController.convert");
        return currencyUseCase.convert(currencyConverterRequest.getAmount(),
                currencyConverterRequest.getCurrencyFrom(),
                currencyConverterRequest.getCurrencyTo())
                .map(currencyConverterResponse ->
                    CurrencyConverterWebResponse
                            .builder()
                            .amount(currencyConverterResponse.getAmount())
                            .exchangeAmount(currencyConverterResponse.getExchangeAmount())
                            .currencyFrom(currencyConverterResponse.getCurrencyFrom())
                            .currencyTo(currencyConverterResponse.getCurrencyTo())
                            .rate(currencyConverterResponse.getRate())
                            .build())
                .map(currencyConverterWebResponse ->
                        ResponseEntity.ok(currencyConverterWebResponse))
                .subscribeOn(Schedulers.io())
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}
