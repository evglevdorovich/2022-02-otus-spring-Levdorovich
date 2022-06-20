package ru.otus.library.exceptions;

public class AuthenticationException extends RuntimeException {
    private static final String CANNOT_AUTHENTICATE_PATTERN = "Cannot authenticate";

    public AuthenticationException() {
        super(CANNOT_AUTHENTICATE_PATTERN);
    }

    public AuthenticationException(String username) {
        super(CANNOT_AUTHENTICATE_PATTERN + ", user: " + username);
    }
}
