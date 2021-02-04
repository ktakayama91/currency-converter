package com.bcp.challenge.usecase.business.exceptions;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
public class CurrencyNotFoundException extends RuntimeException{
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
