package com.epam.recommendationservice.controller;

import com.epam.recommendationservice.converter.CryptoListToSummaryConverter;
import com.epam.recommendationservice.converter.CryptoSymbolToFileConverter;
import com.epam.recommendationservice.model.Crypto;
import com.epam.recommendationservice.model.CryptoSummary;
import com.epam.recommendationservice.parser.CryptoCsvFileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

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
        List<Crypto> cryptoHistoryList = cryptoCsvFileParser.parse(cryptoDataFile);
        return cryptoListToSummaryConverter.convert(cryptoHistoryList);
    }

}
