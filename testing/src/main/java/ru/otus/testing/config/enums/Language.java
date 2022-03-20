package ru.otus.testing.config.enums;

import lombok.Getter;

public enum Language {
    RU("ru"), ROOT("");

    @Getter
    private final String localePath;

    Language(String localePath) {
        this.localePath = localePath;
    }
}
