package com.epam.recommendationservice.converter;

import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CryptoSymbolToFileConverter {
    //TODO: move allowed cryptos validation to another class and to RecommenderController
    private static final Set<String> ALLOWED_CRYPTOS = Set.of("BTC", "DOGE", "ETH", "LTC", "XRP");
    private static final String FILE_PATH_TEMPLATE = "prices/%s_values.csv";

    public File convert(String symbol) {
        if (!ALLOWED_CRYPTOS.contains(symbol)) {
            throw new IllegalArgumentException();
        }

        String path = String.format(FILE_PATH_TEMPLATE, symbol);
        try {
            return new File(ClassLoader.getSystemResource(path).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<File> convertAllFiles() {
        return ALLOWED_CRYPTOS.stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
