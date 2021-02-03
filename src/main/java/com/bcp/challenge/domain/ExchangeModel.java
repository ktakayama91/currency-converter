package com.bcp.challenge.domain;

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
public class ExchangeModel {
    private CurrencyModel from;
    private CurrencyModel to;
    private Double rate;
}
