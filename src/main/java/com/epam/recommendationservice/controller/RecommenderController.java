package com.epam.recommendationservice.controller;

import com.epam.recommendationservice.converter.CryptoSymbolToFileConverter;
import com.epam.recommendationservice.model.Crypto;
import com.epam.recommendationservice.parser.CsvFileParser;
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
    private CsvFileParser csvFileParser;
    @Autowired
    private CryptoSymbolToFileConverter cryptoSymbolToFileConverter;

    @GetMapping("/{symbol}")
    public List<Crypto> getCryptoPrices(@PathVariable(name = "symbol") String symbol) {
        File cryptoDataFile = cryptoSymbolToFileConverter.convert(symbol);
        return csvFileParser.parse(cryptoDataFile);
    }

}
