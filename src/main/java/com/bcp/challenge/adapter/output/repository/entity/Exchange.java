package com.bcp.challenge.adapter.output.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Kei Takayama
 * Created on 2/02/21.
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="currency_from_id", nullable=false)
    private Currency currencyFrom;

    @OneToOne
    @JoinColumn(name="currency_to_id", nullable=false)
    private Currency currencyTo;

    private Double rate;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
