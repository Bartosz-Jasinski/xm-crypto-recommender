package com.epam.recommendationservice.controller;

import com.epam.recommendationservice.converter.CryptoListToSummaryConverter;
import com.epam.recommendationservice.converter.CryptoSymbolToFileConverter;
import com.epam.recommendationservice.model.CryptoNormalized;
import com.epam.recommendationservice.model.CryptoSummary;
import com.epam.recommendationservice.parser.CryptoCsvFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommenderController {
    @Autowired
    private CryptoCsvFileParser cryptoCsvFileParser;
    @Autowired
    private CryptoSymbolToFileConverter cryptoSymbolToFileConverter;
    @Autowired
    private CryptoListToSummaryConverter cryptoListToSummaryConverter;

    @GetMapping("/{symbol}")
    public CryptoSummary getCryptoSummary(@PathVariable(name = "symbol") String symbol) {
        File cryptoDataFile = cryptoSymbolToFileConverter.convert(symbol);
        var cryptoHistoryList = cryptoCsvFileParser.parse(cryptoDataFile);
        return cryptoListToSummaryConverter.convert(cryptoHistoryList);
    }

    @GetMapping("/comparision")
    public TreeSet<CryptoNormalized> getCryptoComparision() {
        var cryptoDataFiles = cryptoSymbolToFileConverter.convertAllFiles();
        var symbolToCryptoHistory = cryptoCsvFileParser.parseMany(cryptoDataFiles);
        var symbolToCryptoSummary = cryptoListToSummaryConverter.convertAllCryptos(symbolToCryptoHistory);
        var normalizedCryptosSorted = new TreeSet<>(new DescendedNormalizedCryptoComparator());
        symbolToCryptoSummary.forEach((key, value) -> normalizedCryptosSorted.add(new CryptoNormalized(key, value.getNormalizedRange())));

        return normalizedCryptosSorted;
    }
}

class DescendedNormalizedCryptoComparator implements Comparator<CryptoNormalized> {
    @Override public int compare(CryptoNormalized cs1, CryptoNormalized cs2)
    {
        return -(cs1.getNormalizedValue()).compareTo(cs2.getNormalizedValue());
    }
}