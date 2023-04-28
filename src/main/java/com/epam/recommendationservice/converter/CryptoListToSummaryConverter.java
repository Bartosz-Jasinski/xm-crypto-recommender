package com.epam.recommendationservice.converter;

import com.epam.recommendationservice.model.Crypto;
import com.epam.recommendationservice.model.CryptoSummary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CryptoListToSummaryConverter {
    public CryptoSummary convert(List<Crypto> cryptos) {
        CryptoSummary cryptoSummary = new CryptoSummary();

        cryptos.forEach(cryptoSummary::update);

        return cryptoSummary;
    }

    public Map<String, CryptoSummary> convertAllCryptos(Map<String, List<Crypto>> symbolToCryptoHistory) {
        Map<String, CryptoSummary> symbolToCryptoSummary = new HashMap<>();
        symbolToCryptoHistory.forEach((key, value) -> symbolToCryptoSummary.put(key, convert(value)));

        return symbolToCryptoSummary;
    }
}
