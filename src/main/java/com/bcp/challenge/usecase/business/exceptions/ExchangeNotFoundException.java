package com.bcp.challenge.usecase.business.exceptions;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
public class ExchangeNotFoundException extends RuntimeException {
    public ExchangeNotFoundException(String message) {
        super(message);
    }
}
