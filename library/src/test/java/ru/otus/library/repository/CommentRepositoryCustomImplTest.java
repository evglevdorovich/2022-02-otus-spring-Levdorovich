package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.domain.Comment;
import ru.otus.library.exceptions.InvalidDataForUpdateException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("Book Repository DAO Test should:")
class CommentRepositoryCustomImplTest {

    private static final long FIRST_COMMENT_ID = 1;
    private static final long UN_EXISTING_COMMENT_ID = 4;

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CommentRepositoryCustomImpl commentRepositoryCustom;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("correct delete comment")
    void shouldDeleteComment() {
        var comment = entityManager.find(Comment.class, FIRST_COMMENT_ID);
        commentRepositoryCustom.deleteExistingCommentById(comment.getId());
        assertThat(entityManager.find(Comment.class, FIRST_COMMENT_ID)).isNull();

    }

    @Test
    @DisplayName("throw InvalidDataForUpdateException while deleting unexisting column")
    void shouldThrowInvalidDataForUpdateExceptionWithUnExistingComment() {
        assertThatThrownBy(() -> commentRepositoryCustom.deleteExistingCommentById(UN_EXISTING_COMMENT_ID))
                .isInstanceOf(InvalidDataForUpdateException.class);
    }


}