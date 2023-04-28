package com.epam.recommendationservice.converter;

import com.epam.recommendationservice.model.Crypto;
import com.epam.recommendationservice.model.CryptoStats;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converter that uses List of {@link Crypto} objects as an input, and creates a {@link CryptoStats},
 * which contains statistics about Crypto coming from an input file with price measurements.
 */
@Service
public class CryptoListToStatsConverter {
    public CryptoStats convert(List<Crypto> cryptos) {
        CryptoStats cryptoStats = new CryptoStats();
        cryptos.forEach(cryptoStats::update);

        return cryptoStats;
    }

    /**
     * Converts (per crypto) every measurement coming from a data file to a {@link CryptoStats}
     *
     * @param symbolToCryptoHistory - {@link Crypto#getSymbol()} for every supported crypto mapped to a list of all
     *                              crypto measurements from a data file
     * @return {@link Crypto#getSymbol()} for every supported crypto mapped to a {@link CryptoStats}
     */
    public Map<String, CryptoStats> convertAllCryptos(Map<String, List<Crypto>> symbolToCryptoHistory) {
        Map<String, CryptoStats> symbolToCryptoSummary = new HashMap<>();
        symbolToCryptoHistory.forEach((key, value) -> symbolToCryptoSummary.put(key, convert(value)));

        return symbolToCryptoSummary;
    }
}
