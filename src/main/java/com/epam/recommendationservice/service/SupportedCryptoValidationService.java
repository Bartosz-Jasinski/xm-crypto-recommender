package com.epam.recommendationservice.service;

import com.epam.recommendationservice.exception.CryptoDataFileMissingException;
import com.epam.recommendationservice.exception.UnsupportedCryptoException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class SupportedCryptoValidationService {
    public static final String FILENAME_SUFFIX = "_values.csv";
    public static final String CRYPTO_DATA_PATH = "prices/";
    private Set<String> allowedCryptos;

    public Set<String> getAllowedCryptos() {
        if (allowedCryptos == null) {
            fetchAllowedCryptos();
        }

        return allowedCryptos;
    }

    /**
     * Fetches a collection of supported crypto symbols and sets it to the {@link #allowedCryptos}.
     * It is scheduled to run every 6 hours, in case the supported cryptos changes.
     */
    @Scheduled(cron = "0 0 */6 * * *")
    private void fetchAllowedCryptos() {
        File pricesFolder;
        try {
            pricesFolder = new File(ClassLoader.getSystemResource(CRYPTO_DATA_PATH).toURI());
        } catch (URISyntaxException e) {
            throw new CryptoDataFileMissingException("Wrong path to a directory with crypto data.", e);
        }

        allowedCryptos = new HashSet<>();
        Arrays.stream(Objects.requireNonNull(pricesFolder.listFiles()))
                .map(File::getName)
                .map(name -> name.replace(FILENAME_SUFFIX, ""))
                .forEach(allowedCryptos::add);
    }

    public void validateSupportedCrypto(String symbol) {
        if (allowedCryptos == null) {
            fetchAllowedCryptos();
        }

        if (!allowedCryptos.contains(symbol)) {
            throw new UnsupportedCryptoException("Crypto " + symbol + " not supported");
        }
    }
}
