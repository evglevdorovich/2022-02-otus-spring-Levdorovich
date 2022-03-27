package ru.otus.testing.exceptions;

import java.util.NoSuchElementException;

public class NoSuchLocaleException extends NoSuchElementException {

    public NoSuchLocaleException(String locale) {
        super("locale: " + locale + " isn't found");
    }
}
