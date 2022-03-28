package ru.otus.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.EmptyResultException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbc;

    @Override
    public List<Genre> getAll() {
        try {
            return namedParameterJdbc.query("select id, name from genres", new GenreMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultException();
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            var id = resultSet.getLong("id");
            var name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
