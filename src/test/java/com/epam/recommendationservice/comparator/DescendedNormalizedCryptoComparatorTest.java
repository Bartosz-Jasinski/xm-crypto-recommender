package com.epam.recommendationservice.comparator;

import com.epam.recommendationservice.model.CryptoNormalized;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.TreeSet;

public class DescendedNormalizedCryptoComparatorTest {
    @Test
    public void testTreeSetIsProperlySorted() {
        var normalizedCryptosSorted = new TreeSet<>(new DescendedNormalizedCryptoComparator());
        normalizedCryptosSorted.add(new CryptoNormalized("LOW", BigDecimal.valueOf(0.1)));
        normalizedCryptosSorted.add(new CryptoNormalized("MDM", BigDecimal.valueOf(0.5)));
        normalizedCryptosSorted.add(new CryptoNormalized("HGH", BigDecimal.valueOf(0.9)));

        Assertions.assertEquals(BigDecimal.valueOf(0.9), normalizedCryptosSorted.first().getNormalizedValue());
        Assertions.assertEquals(BigDecimal.valueOf(0.1), normalizedCryptosSorted.last().getNormalizedValue());
    }
}
