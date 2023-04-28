package com.epam.recommendationservice.converter;

import com.epam.recommendationservice.exception.CryptoDataFileMissingException;
import com.epam.recommendationservice.service.SupportedCryptoValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Converter that uses crypto symbol to find and retrieve a corresponding data file from a resources.
 */
@Service
@RequiredArgsConstructor
public class CryptoSymbolToFileConverter {
    private final SupportedCryptoValidationService supportedCryptoValidationService;
    private static final String FILE_PATH_TEMPLATE = "prices/%s_values.csv";

    public File convert(String symbol) {
        supportedCryptoValidationService.validateSupportedCrypto(symbol);

        String path = String.format(FILE_PATH_TEMPLATE, symbol);
        try {
            return new File(ClassLoader.getSystemResource(path).toURI());
        } catch (URISyntaxException e) {
            throw new CryptoDataFileMissingException("No data file for crypto " + symbol + " found.", e);
        }
    }

    public Set<File> convertAllFiles() {
        return supportedCryptoValidationService.getAllowedCryptos().stream()
                .map(this::convert)
                .collect(Collectors.toSet());
    }
}
