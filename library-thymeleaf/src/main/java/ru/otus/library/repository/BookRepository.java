package ru.otus.library.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
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
