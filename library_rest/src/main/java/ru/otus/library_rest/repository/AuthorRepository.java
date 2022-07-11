package ru.otus.library_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.library_rest.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
