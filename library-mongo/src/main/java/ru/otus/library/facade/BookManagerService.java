package ru.otus.library.facade;

public interface BookManagerService {
    String getAllViews();

    void insert(String name, String genreName, String authorName);

    void deleteById(String id);

    String getViewById(String id);

    void update(String id, String bookName, String genreName, String authorName);
}
