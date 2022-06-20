package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    @EntityGraph(attributePaths = {"genre", "author"})
    Optional<Book> findById(long id);

    @Override
    @EntityGraph(attributePaths = {"genre", "author"})
    List<Book> findAll();

}
