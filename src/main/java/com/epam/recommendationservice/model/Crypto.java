package com.epam.recommendationservice.model;

import com.epam.recommendationservice.converter.LocalDateTimeConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents single row from CSV file.
 * Contains information about crypto price measurement.
 */
@Getter
@Setter
public class Crypto {
    @CsvCustomBindByName(column = "timestamp", converter = LocalDateTimeConverter.class)
    private LocalDateTime measurementDateTime;

    @CsvBindByName(column = "symbol")
    private String symbol;

    @CsvBindByName(column = "price")
    private BigDecimal priceUsd;
}
