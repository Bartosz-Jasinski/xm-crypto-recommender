package com.epam.recommendationservice.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class LocalDateTimeConverter extends AbstractBeanField<LocalDateTime, String> {
    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(s)),
                TimeZone.getDefault().toZoneId());
    }
}
