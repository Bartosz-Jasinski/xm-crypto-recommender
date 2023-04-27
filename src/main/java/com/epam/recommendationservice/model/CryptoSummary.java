package com.epam.recommendationservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoSummary {
    private Crypto oldest;
    private Crypto newest;
    private Crypto max;
    private Crypto min;

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
}
