package com.epam.recommendationservice.comparator;

import com.epam.recommendationservice.model.CryptoNormalized;

import java.util.Comparator;

public class DescendedNormalizedCryptoComparator implements Comparator<CryptoNormalized> {
    /**
     * Compares two {@link CryptoNormalized} objects with descending rule.
     * @param cs1 the first object to be compared.
     * @param cs2 the second object to be compared.
     * @return 1, 0, or -1 as cs1 is numerically less than, equal to, or greater than cs2.
     */
    @Override
    public int compare(CryptoNormalized cs1, CryptoNormalized cs2) {
        return -(cs1.getNormalizedValue().compareTo(cs2.getNormalizedValue()));
    }
}