package com.epam.recommendationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Represents crypto with normalized value.
 */
@Getter
@Setter
@AllArgsConstructor
public class CryptoNormalized {
    private String symbol;
    /**
     * Result of calculating a normalized price {@link CryptoStats#getNormalizedRange()}
     */
    private BigDecimal normalizedValue;
}
