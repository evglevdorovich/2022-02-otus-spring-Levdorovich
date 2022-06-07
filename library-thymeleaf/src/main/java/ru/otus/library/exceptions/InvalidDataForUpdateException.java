package ru.otus.library.exceptions;

public class InvalidDataForUpdateException extends CannotUpdateException {

    private static final String INVALID_DATA = "Data is invalid";

    public InvalidDataForUpdateException() {
        super(INVALID_DATA);
    }

    public InvalidDataForUpdateException(String message) {
        super(message);
    }
}
