package com.bcp.challenge.adapter.input.web;

import com.bcp.challenge.adapter.input.web.request.ExchangeWebRequest;
import com.bcp.challenge.adapter.input.web.response.ExchangeWebResponse;
import com.bcp.challenge.usecase.port.input.ExchangeUseCase;
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
@RequestMapping("${application.exchange.url}")
public class ExchangeController {

    private final ExchangeUseCase exchangeUseCase;

    public ExchangeController(ExchangeUseCase exchangeUseCase) {
        this.exchangeUseCase = exchangeUseCase;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Single<ResponseEntity<ExchangeWebResponse>> updateExchange(@RequestBody ExchangeWebRequest exchangeWebRequest) {
        log.info("Init ExchangeController.updateExchange");
        return exchangeUseCase.updateExchange(exchangeWebRequest.getRate(),
                exchangeWebRequest.getCurrencyFrom(),
                exchangeWebRequest.getCurrencyTo())
                .map(exchangeResponse ->
                        ExchangeWebResponse
                                .builder()
                                .currencyFrom(exchangeResponse.getCurrencyFrom())
                                .currencyTo(exchangeResponse.getCurrencyTo())
                                .rate(exchangeResponse.getRate())
                                .build())
                .map(exchangeWebResponse ->
                        ResponseEntity.ok(exchangeWebResponse))
                .subscribeOn(Schedulers.io())
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }
}
