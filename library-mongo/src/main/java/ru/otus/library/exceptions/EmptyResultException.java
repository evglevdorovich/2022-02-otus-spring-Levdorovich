package ru.otus.library.exceptions;

public class EmptyResultException extends RuntimeException {
    private static final String RESULT_EMPTY_MESSAGE = "Result for your query is empty";

    public EmptyResultException() {
        super(RESULT_EMPTY_MESSAGE);
    }

    public EmptyResultException(String message) {
        super(message);
    }
}
