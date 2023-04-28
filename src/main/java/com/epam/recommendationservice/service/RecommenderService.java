package com.epam.recommendationservice.service;

import com.epam.recommendationservice.comparator.DescendedNormalizedCryptoComparator;
import com.epam.recommendationservice.converter.CryptoListToStatsConverter;
import com.epam.recommendationservice.converter.CryptoSymbolToFileConverter;
import com.epam.recommendationservice.model.CryptoNormalized;
import com.epam.recommendationservice.model.CryptoStats;
import com.epam.recommendationservice.parser.CryptoCsvFileParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class RecommenderService {
    private final CryptoCsvFileParser cryptoCsvFileParser;
    private final CryptoSymbolToFileConverter cryptoSymbolToFileConverter;
    private final CryptoListToStatsConverter cryptoListToStatsConverter;

    /**
     * Fetches a {@link CryptoStats} for a cryptocurrency specified in a param
     *
     * @param symbol - crypto symbol which statistics should be returned
     * @return statistics of a crypto created from a corresponding data file
     */
    public CryptoStats getCryptoSummary(String symbol) {
        File cryptoDataFile = cryptoSymbolToFileConverter.convert(symbol);
        var cryptoHistoryList = cryptoCsvFileParser.parse(cryptoDataFile);
        return cryptoListToStatsConverter.convert(cryptoHistoryList);
    }

    /**
     * Fetches a descending sorted set of a {@link CryptoNormalized} cryptos, supported by the system.
     *
     * @return set of a cryptos with normalized prices calculated
     */
    @GetMapping("/comparision")
    public TreeSet<CryptoNormalized> getCryptoComparision() {
        var cryptoDataFiles = cryptoSymbolToFileConverter.convertAllFiles();
        var symbolToCryptoHistory = cryptoCsvFileParser.parseMany(cryptoDataFiles);
        var symbolToCryptoSummary = cryptoListToStatsConverter.convertAllCryptos(symbolToCryptoHistory);
        var normalizedCryptosSorted = new TreeSet<>(new DescendedNormalizedCryptoComparator()); // extract as a field
        symbolToCryptoSummary.forEach((key, value) -> normalizedCryptosSorted.add(new CryptoNormalized(key, value.getNormalizedRange())));

        return normalizedCryptosSorted;
    }
}
