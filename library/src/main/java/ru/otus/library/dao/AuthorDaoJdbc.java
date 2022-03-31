package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;
import ru.otus.library.exceptions.EmptyResultException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbc;

    @Override
    public List<Author> getAll() {
        try {
            return namedParameterJdbc.query("select id, name from authors", new AuthorMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultException();
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            var id = resultSet.getLong("id");
            var name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
