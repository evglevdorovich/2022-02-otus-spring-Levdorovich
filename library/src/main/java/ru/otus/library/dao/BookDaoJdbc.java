package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;
import ru.otus.library.exceptions.CannotUpdateException;
import ru.otus.library.exceptions.EmptyResultException;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbc;

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            var id = resultSet.getLong("id");
            var name = resultSet.getString("name");
            var genreId = resultSet.getLong("genre_id");
            var authorId = resultSet.getLong("author_id");
            return new Book(id, name, genreId, authorId);
        }
    }

    @Override
    public List<Book> getAll() {
        try {
            return namedParameterJdbc.query("select id, name, genre_id, author_id from books", new BookMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultException();
        }
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return namedParameterJdbc.queryForObject("select id, name, genre_id, author_id from books where id =:id ",
                    params, new BookMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultException();
        }
    }

    @Override
    public void insert(Book book) {
        try {
            if (book.getId() == 0) {
                namedParameterJdbc.update("insert into books (name, genre_id, author_id) values (:name,:genreId," +
                        ":authorId)", Map.of("name", book.getName(),
                        "genreId", book.getGenreId(), "authorId", book.getAuthorId()));
            } else {
                namedParameterJdbc.update("insert into books (id, name, genre_id, author_id) values (:id, :name, " +
                        ":genreId, :authorId)", Map.of("id", book.getId(), "name", book.getName()
                        , "genreId", book.getGenreId(), "authorId", book.getAuthorId()));
            }
        } catch (DataIntegrityViolationException exception) {
            throw new InvalidDataForUpdateException();
        } catch (DataAccessException dataAccessException) {
            throw new CannotUpdateException(dataAccessException.getMessage());
        }
    }

    @Override
    public void update(Book book) {
        try {
            namedParameterJdbc.update("update books set name = :name, genre_id = :genreId, author_id = :authorId " +
                            "where id = :id",
                    Map.of("name", book.getName(), "genreId", book.getGenreId(), "authorId", book.getAuthorId(),
                            "id", book.getId()));
        } catch (DataIntegrityViolationException exception) {
            throw new InvalidDataForUpdateException();
        } catch (DataAccessException dataAccessException) {
            throw new CannotUpdateException(dataAccessException.getMessage());
        }
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbc.update("delete from books where id = :id", Collections.singletonMap("id", id));
    }

}
