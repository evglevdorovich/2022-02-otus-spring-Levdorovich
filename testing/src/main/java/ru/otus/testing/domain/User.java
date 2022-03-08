package ru.otus.testing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private String firstName;
    private String lastName;
}
