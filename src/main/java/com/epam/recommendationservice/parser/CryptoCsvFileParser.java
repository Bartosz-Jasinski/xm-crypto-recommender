package com.epam.recommendationservice.parser;

import com.epam.recommendationservice.exception.CryptoDataFileMissingException;
import com.epam.recommendationservice.model.Crypto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

@Service
public class CryptoCsvFileParser implements FileParser {

    @Override
    public List<Crypto> parse(File file) {
        List<Crypto> cryptos;
        try {
            cryptos = new CsvToBeanBuilder<Crypto>(new FileReader(file))
                    .withType(Crypto.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new CryptoDataFileMissingException(file.getName() + " for parsing not found.");
        }

        return cryptos;
    }

    public Map<String, List<Crypto>> parseMany(Set<File> files) {
        Map<String, List<Crypto>> symbolToCryptos = new HashMap<>();
        files.forEach(file -> {
            List<Crypto> cryptos = parse(file);
            symbolToCryptos.put(cryptos.get(0).getSymbol(), cryptos);
        });

        return symbolToCryptos;
    }
}
