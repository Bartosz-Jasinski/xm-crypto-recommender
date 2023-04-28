package com.epam.recommendationservice.service;

import com.epam.recommendationservice.comparator.DescendedNormalizedCryptoComparator;
import com.epam.recommendationservice.controller.RecommenderController;
import com.epam.recommendationservice.converter.CryptoListToStatsConverter;
import com.epam.recommendationservice.converter.CryptoSymbolToFileConverter;
import com.epam.recommendationservice.model.CryptoNormalized;
import com.epam.recommendationservice.model.CryptoStats;
import com.epam.recommendationservice.parser.CryptoCsvFileParser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class RecommenderService {
    private final CryptoCsvFileParser cryptoCsvFileParser;
    private final CryptoSymbolToFileConverter cryptoSymbolToFileConverter;
    private final CryptoListToStatsConverter cryptoListToStatsConverter;

    private Set<CryptoNormalized> normalizedCryptosSorted;

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
     * Returns {@link #normalizedCryptosSorted}.
     * If {@link #normalizedCryptosSorted} is not calculated already, method calls {@link #calculateNormalizedCryptos()}
     * to calculate the data.
     *
     * @return set of a cryptos with normalized prices calculated
     */
    public Set<CryptoNormalized> getNormalizedCryptosSorted() {
        if (normalizedCryptosSorted == null) {
            calculateNormalizedCryptos();
        }

        return normalizedCryptosSorted;
    }

    /**
     * Calculates normalized prices for all cryptos supported by the system.
     * It is scheduled to run every 6 hours, in case the data inside the files changes and to limit the run time when
     * the {@link RecommenderController#getCryptoComparision()} is called.
     */
    @Scheduled(cron = "0 0 */6 * * *")
    private void calculateNormalizedCryptos() {
        var cryptoDataFiles = cryptoSymbolToFileConverter.convertAllFiles();
        var symbolToCryptoHistory = cryptoCsvFileParser.parseMany(cryptoDataFiles);
        var symbolToCryptoSummary = cryptoListToStatsConverter.convertAllCryptos(symbolToCryptoHistory);
        normalizedCryptosSorted = new TreeSet<>(new DescendedNormalizedCryptoComparator());
        symbolToCryptoSummary.forEach((key, value) -> normalizedCryptosSorted.add(new CryptoNormalized(key, value.getNormalizedRange())));
    }
}
