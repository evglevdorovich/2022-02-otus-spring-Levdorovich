package ru.otus.testing.exceptions;

public class CsvValidateException extends RuntimeException {
    public CsvValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
