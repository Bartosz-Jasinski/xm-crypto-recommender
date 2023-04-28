package com.epam.recommendationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CryptoNormalized {
    private String symbol;
    private BigDecimal normalizedValue;
}
