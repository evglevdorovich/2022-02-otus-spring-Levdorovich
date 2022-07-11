package ru.otus.library_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.library_rest.domain.Book;
import ru.otus.library_rest.projections.BookWithAuthorAndGenreProjection;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = BookWithAuthorAndGenreProjection.class)
public interface BookRepository extends JpaRepository<Book, Long> {

}
