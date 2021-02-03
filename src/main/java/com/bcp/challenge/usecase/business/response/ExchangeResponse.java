package com.bcp.challenge.usecase.business.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
@Getter
@Setter
@Builder
public class ExchangeResponse {
    private String currencyFrom;
    private String currencyTo;
    private Double rate;
}
