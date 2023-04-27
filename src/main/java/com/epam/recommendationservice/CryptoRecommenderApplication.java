package com.epam.recommendationservice;

import com.epam.recommendationservice.model.Crypto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootApplication
public class CryptoRecommenderApplication {

	public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
		SpringApplication.run(CryptoRecommenderApplication.class, args);

		File file = new File(ClassLoader.getSystemResource("prices/BTC_values.csv").toURI());
		List<Crypto> bitcoin = new CsvToBeanBuilder<Crypto>(new FileReader(file))
				.withType(Crypto.class)
				.build()
				.parse();
		System.out.println(bitcoin.size());
	}

}
