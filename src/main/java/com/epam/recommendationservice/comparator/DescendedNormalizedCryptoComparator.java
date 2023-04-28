package com.epam.recommendationservice.comparator;

import com.epam.recommendationservice.model.CryptoNormalized;

import java.util.Comparator;

public class DescendedNormalizedCryptoComparator implements Comparator<CryptoNormalized> {
    @Override
    public int compare(CryptoNormalized cs1, CryptoNormalized cs2) {
        return -(cs1.getNormalizedValue()).compareTo(cs2.getNormalizedValue());
    }
}