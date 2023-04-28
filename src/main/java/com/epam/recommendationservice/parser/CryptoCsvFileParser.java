package com.epam.recommendationservice.parser;

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
        List<Crypto> cryptos = new ArrayList<>();
        try {
            cryptos = new CsvToBeanBuilder<Crypto>(new FileReader(file))
                    .withType(Crypto.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            System.out.println("error");
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
