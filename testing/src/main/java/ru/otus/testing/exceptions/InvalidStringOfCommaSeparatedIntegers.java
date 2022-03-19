package ru.otus.testing.exceptions;

public class InvalidStringOfCommaSeparatedIntegers extends RuntimeException {
    public InvalidStringOfCommaSeparatedIntegers(String message) {
        super(message);
    }
}
