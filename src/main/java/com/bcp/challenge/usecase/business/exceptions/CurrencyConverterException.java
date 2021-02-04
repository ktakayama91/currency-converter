package com.bcp.challenge.usecase.business.exceptions;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
public class CurrencyConverterException extends RuntimeException{
    public CurrencyConverterException(String message) {
        super(message);
    }
}
