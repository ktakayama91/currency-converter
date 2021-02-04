package com.bcp.challenge.usecase.business.exceptions;

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
public class BaseError {
    private int status;
    private String message;
}
