package ru.otus.library.repository;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("comment repository Jpa should : ")
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    private static final long FIRST_COMMENT_ID = 1;
    private static final long FIRST_COMMENT_BOOK_ID = 1;
    private static final long SECOND_COMMENT_ID = 2;
    private static final long SECOND_COMMENT_BOOK_ID = 2;
    private static final long THIRD_COMMENT_ID = 3;
    private static final long UN_EXISTING_COMMENT_ID = 4;
    private static final String FIRST_COMMENT_TEXT = "bad";
    private static final String SECOND_COMMENT_TEXT = "good";
    private static final String THIRD_COMMENT_TEXT = "excellent";

    private static final String FIRST_BOOK_NAME = "December 1941";
    private static final long FIRST_BOOK_GENRE_ID = 1;
    private static final String FIRST_BOOK_GENRE_NAME = "myth";
    private static final long FIRST_BOOK_AUTHOR_ID = 1;
    private static final String FIRST_BOOK_AUTHOR_NAME = "Harper Lee";
    private static final long SECOND_BOOK_ID = 2;
    private static final String SECOND_BOOK_NAME = "The Inevitable";
    private static final long SECOND_BOOK_GENRE_ID = 2;
    private static final String SECOND_BOOK_GENRE_NAME = "romance";
    private static final long SECOND_BOOK_AUTHOR_ID = 2;
    private static final String SECOND_BOOK_AUTHOR_NAME = "Velma Walls";

    @Test
    @DisplayName("return expected comment")
    void shouldReturnExpectedComment() {
        var book = Book.builder().id(FIRST_COMMENT_BOOK_ID)
                .author(new Author(FIRST_BOOK_AUTHOR_ID, FIRST_BOOK_AUTHOR_NAME))
                .genre(new Genre(FIRST_BOOK_GENRE_ID, FIRST_BOOK_GENRE_NAME))
                .name(FIRST_BOOK_NAME).build();

        var expectedComment = new Comment(FIRST_COMMENT_ID, FIRST_COMMENT_TEXT, book);
        var optionalActualComment = commentRepositoryJpa.getById(expectedComment.getId());
        assertTrue(optionalActualComment.isPresent());
        var actualComment = optionalActualComment.get();
        var actualBook = actualComment.getBook();
        var unProxiedGenre = Hibernate.unproxy(actualBook.getGenre(), Genre.class);
        var unProxiedAuthor = Hibernate.unproxy(actualBook.getAuthor(), Author.class);
        actualBook.setGenre(unProxiedGenre);
        actualBook.setAuthor(unProxiedAuthor);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void shouldReturnExpectedComments() {
        var book = Book.builder().id(SECOND_COMMENT_BOOK_ID)
                .author(new Author(SECOND_BOOK_AUTHOR_ID, SECOND_BOOK_AUTHOR_NAME))
                .genre(new Genre(SECOND_BOOK_GENRE_ID, SECOND_BOOK_GENRE_NAME))
                .name(SECOND_BOOK_NAME).build();
        var firstExpectedComment = new Comment(SECOND_COMMENT_ID, SECOND_COMMENT_TEXT, book);
        var secondExpectedComment = new Comment(THIRD_COMMENT_ID, THIRD_COMMENT_TEXT, book);
        var expectedComments = List.of(firstExpectedComment, secondExpectedComment);
        var actualComments = commentRepositoryJpa.getByBookId(SECOND_BOOK_ID);

        for (var comment : actualComments) {
            var actualBook = comment.getBook();
            var unProxiedGenre = Hibernate.unproxy(actualBook.getGenre(), Genre.class);
            var unProxiedAuthor = Hibernate.unproxy(actualBook.getAuthor(), Author.class);
            actualBook.setGenre(unProxiedGenre);
            actualBook.setAuthor(unProxiedAuthor);
        }

        assertThat(actualComments).containsExactlyInAnyOrderElementsOf(expectedComments);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("correct delete comment")
    void shouldDeleteComment() {
        var comment = entityManager.find(Comment.class, FIRST_COMMENT_ID);
        commentRepositoryJpa.deleteById(comment.getId());
        assertThat(entityManager.find(Comment.class, FIRST_COMMENT_ID)).isNull();

    }

    @Test
    @DisplayName("throw InvalidDataForUpdateException while deleting unexisting column")
    void shouldThrowInvalidDataForUpdateExceptionWithUnExistingComment() {
        assertThatThrownBy(() -> commentRepositoryJpa.deleteById(UN_EXISTING_COMMENT_ID))
                .isInstanceOf(InvalidDataForUpdateException.class);
    }

    @Test
    @DisplayName("should correct save comment")
    void shouldSaveComment() {
        var book = Book.builder().id(SECOND_COMMENT_ID)
                .author(new Author(SECOND_BOOK_AUTHOR_ID, SECOND_BOOK_AUTHOR_NAME))
                .genre(new Genre(SECOND_BOOK_GENRE_ID, SECOND_BOOK_GENRE_NAME))
                .name(SECOND_BOOK_NAME).build();
        var expectedComment = new Comment(UN_EXISTING_COMMENT_ID,"some text",book);

        commentRepositoryJpa.saveOrUpdate(expectedComment);

        var actualComment = entityManager.find(Comment.class, UN_EXISTING_COMMENT_ID);
        var proxiedBook = actualComment.getBook();
        var unProxiedBook = Hibernate.unproxy(proxiedBook, Book.class);
        actualComment.setBook(unProxiedBook);
        var unProxiedGenre = Hibernate.unproxy(unProxiedBook.getGenre(), Genre.class);
        var unProxiedAuthor = Hibernate.unproxy(unProxiedBook.getAuthor(), Author.class);
        unProxiedBook.setAuthor(unProxiedAuthor);
        unProxiedBook.setGenre(unProxiedGenre);

        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("should correct update comments")
    void shouldCorrectUpdateComments(){
        var book = Book.builder().id(SECOND_COMMENT_ID)
                .author(new Author(SECOND_BOOK_AUTHOR_ID, SECOND_BOOK_AUTHOR_NAME))
                .genre(new Genre(SECOND_BOOK_GENRE_ID, SECOND_BOOK_GENRE_NAME))
                .name(SECOND_BOOK_NAME).build();

        var expectedComment = new Comment(SECOND_COMMENT_ID,"Changed Text",book);

        commentRepositoryJpa.saveOrUpdate(expectedComment);

        var actualComment = entityManager.find(Comment.class, SECOND_COMMENT_ID);

        var proxiedBook = actualComment.getBook();
        var unProxiedBook = Hibernate.unproxy(proxiedBook, Book.class);
        actualComment.setBook(unProxiedBook);
        var unProxiedGenre = Hibernate.unproxy(unProxiedBook.getGenre(),Genre.class);
        var unProxiedAuthor = Hibernate.unproxy(unProxiedBook.getAuthor(), Author.class);
        unProxiedBook.setGenre(unProxiedGenre);
        unProxiedBook.setAuthor(unProxiedAuthor);

        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }
}