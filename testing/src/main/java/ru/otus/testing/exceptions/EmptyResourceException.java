package ru.otus.testing.exceptions;

public class EmptyResourceException extends RuntimeException {
    public EmptyResourceException(String resourcePath) {
        super("resource at " + resourcePath + " is empty");
    }
}
