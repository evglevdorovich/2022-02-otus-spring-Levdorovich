package ru.otus.library.facade;

public interface BookManagerService {
    String getAllViews();

    void insert(String name, long authorId, long genreId);

    void deleteById(long id);

    String getViewById(long id);

    void updateById(long id, String name, long genreId, long authorId);
}
