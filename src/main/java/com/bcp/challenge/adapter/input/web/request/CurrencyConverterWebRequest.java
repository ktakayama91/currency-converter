package com.bcp.challenge.adapter.input.web.request;

import lombok.Getter;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */

@Getter
public class CurrencyConverterWebRequest {
    private Double amount;
    private String currencyFrom;
    private String currencyTo;
}
