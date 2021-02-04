package com.bcp.challenge.application.config;

import com.bcp.challenge.usecase.business.exceptions.BaseError;
import com.bcp.challenge.usecase.business.exceptions.CurrencyConverterException;
import com.bcp.challenge.usecase.business.exceptions.CurrencyNotFoundException;
import com.bcp.challenge.usecase.business.exceptions.ExchangeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */

@ControllerAdvice
public class CurrencyConverterControllerAdvice {

    @ExceptionHandler(CurrencyNotFoundException.class)
    public ResponseEntity<BaseError> currencyNotFoundException(CurrencyNotFoundException exception) {
        BaseError baseError = BaseError
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseError);
    }

    @ExceptionHandler(ExchangeNotFoundException.class)
    public ResponseEntity<BaseError> exchangeNotFoundException(ExchangeNotFoundException exception) {
        BaseError baseError = BaseError
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseError);
    }

    @ExceptionHandler(CurrencyConverterException.class)
    public ResponseEntity<BaseError> currencyConverterException(CurrencyConverterException exception) {
        BaseError baseError = BaseError
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseError);
    }
}
