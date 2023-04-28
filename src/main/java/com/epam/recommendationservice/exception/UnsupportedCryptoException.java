package com.epam.recommendationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnsupportedCryptoException extends IllegalArgumentException {
    public UnsupportedCryptoException() {
        super();
    }
    public UnsupportedCryptoException(String message, Throwable cause) {
        super(message, cause);
    }
    public UnsupportedCryptoException(String message) {
        super(message);
    }
    public UnsupportedCryptoException(Throwable cause) {
        super(cause);
    }
}
