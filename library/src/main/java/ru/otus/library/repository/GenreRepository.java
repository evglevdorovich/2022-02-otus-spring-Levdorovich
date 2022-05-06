package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
