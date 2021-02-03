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
public class CurrencyModel {
    private Long id;
    private String name;
}
