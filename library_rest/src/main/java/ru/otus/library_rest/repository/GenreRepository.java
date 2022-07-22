package ru.otus.library_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library_rest.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
