package com.epam.recommendationservice.model;

import com.epam.recommendationservice.converter.CryptoListToStatsConverter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Contains a statistics created from a crypto measurements.
 */
@Getter
@Setter
public class CryptoStats {
    private Crypto oldest;
    private Crypto newest;
    private Crypto max;
    private Crypto min;

    /**
     * Tries to update all of this class fields, based on the values from a single measurement.
     * This method is used inside {@link CryptoListToStatsConverter},
     * to create stats about measured crypto prices.
     *
     * @param crypto - contains single measurement from a data file
     */
    public void update(Crypto crypto) {
        updateOldest(crypto);
        updateNewest(crypto);
        updateMax(crypto);
        updateMin(crypto);
    }

    private void updateOldest(Crypto crypto) {
        if (oldest == null || crypto.getMeasurementDateTime().isBefore(oldest.getMeasurementDateTime())) {
            oldest = crypto;
        }
    }

    private void updateNewest(Crypto crypto) {
        if (newest == null || crypto.getMeasurementDateTime().isAfter(newest.getMeasurementDateTime())) {
            newest = crypto;
        }
    }

    private void updateMax(Crypto crypto) {
        if (max == null || crypto.getPriceUsd().compareTo(max.getPriceUsd()) > 0) {
            max = crypto;
        }
    }

    private void updateMin(Crypto crypto) {
        if (min == null || crypto.getPriceUsd().compareTo(min.getPriceUsd()) < 0) {
            min = crypto;
        }
    }

    public BigDecimal getNormalizedRange() {
        BigDecimal subtracted = max.getPriceUsd().subtract(min.getPriceUsd());
        return subtracted.divide(min.getPriceUsd(), 2, RoundingMode.HALF_UP);
    }
}
