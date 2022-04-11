package ru.otus.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
