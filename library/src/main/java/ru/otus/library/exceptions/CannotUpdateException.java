package ru.otus.library.exceptions;

public class CannotUpdateException extends RuntimeException {
    private static final String FAIL_INSERT = "cannot update data";

    public CannotUpdateException() {
        super(FAIL_INSERT);
    }

    public CannotUpdateException(String message) {
        super(message);
    }
}
