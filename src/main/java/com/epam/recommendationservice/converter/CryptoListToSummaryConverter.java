package com.epam.recommendationservice.converter;

import com.epam.recommendationservice.model.Crypto;
import com.epam.recommendationservice.model.CryptoSummary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoListToSummaryConverter {
    public CryptoSummary convert(List<Crypto> cryptos) {
        CryptoSummary cryptoSummary = new CryptoSummary();

        cryptos.forEach(cryptoSummary::update);

        return cryptoSummary;
    }
}
