package com.epam.recommendationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CryptoDataFileMissingException extends RuntimeException {
    public CryptoDataFileMissingException(String message, Throwable cause) {
        super(message, cause);
    }
    public CryptoDataFileMissingException(String message) {
        super(message);
    }
}
