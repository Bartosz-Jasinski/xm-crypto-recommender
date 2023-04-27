package com.epam.recommendationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

@SpringBootApplication
public class CryptoRecommenderApplication {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        SpringApplication.run(CryptoRecommenderApplication.class, args);
    }

}
