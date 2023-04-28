package com.epam.recommendationservice.converter;

import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class LocalDateTimeConverterTest {
    private final LocalDateTimeConverter converter = new LocalDateTimeConverter();

    @Test
    public void testTimestampIsConvertedProperly() throws CsvConstraintViolationException, CsvDataTypeMismatchException {
        LocalDateTime convertedLocalDateTime = (LocalDateTime) converter.convert("1641020400000");
        LocalDateTime createdLocalDateTime = LocalDateTime.of(2022, 1, 1, 8, 0);

        Assertions.assertEquals(createdLocalDateTime, convertedLocalDateTime);
    }

    @Test
    public void testNumberFormatExceptionForIncorrectTimestamp() {
        Assertions.assertThrows(NumberFormatException.class, () -> converter.convert(null));

        Assertions.assertThrows(NumberFormatException.class, () -> converter.convert("test"));
    }
}
