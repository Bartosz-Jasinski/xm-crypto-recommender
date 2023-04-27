package com.epam.recommendationservice.parser;

import com.epam.recommendationservice.model.Crypto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoCsvFileParser implements FileParser {

    @Override
    public List<Crypto> parse(File file) {
        List<Crypto> bitcoin = new ArrayList<>();
        try {
            bitcoin = new CsvToBeanBuilder<Crypto>(new FileReader(file))
                    .withType(Crypto.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            System.out.println("error");
        }

        return bitcoin;
    }
}
