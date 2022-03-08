package ru.otus.testing.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourcePath, Throwable cause) {
        super("resource at " + resourcePath + " hasn't been found", cause);
    }
}
