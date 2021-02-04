package com.bcp.challenge.adapter.output;

import static org.junit.jupiter.api.Assertions.*;

import com.bcp.challenge.adapter.output.repository.CurrencyJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Kei Takayama
 * Created on 3/02/21.
 */
@ExtendWith(MockitoExtension.class)
class CurrencyRepositoryAdapterTest {

    @InjectMocks
    private CurrencyRepositoryAdapter currencyRepositoryAdapter;

    @Mock
    private CurrencyJpaRepository currencyJpaRepository;

    @Test
    public void findAllSuccessTest() {

    }

}