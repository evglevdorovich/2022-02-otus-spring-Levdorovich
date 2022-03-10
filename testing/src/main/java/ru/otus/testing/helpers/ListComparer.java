package ru.otus.testing.helpers;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class ListComparer {

    public <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }
}
