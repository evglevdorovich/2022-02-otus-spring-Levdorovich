package ru.otus.library.converter;

import org.springframework.stereotype.Component;
import ru.otus.library.domain.Comment;

import java.util.List;

@Component
public class CommentViewConverterImpl implements CommentViewConverter {
    private static final String BOOK_ID = "bookId";
    private static final String TEXT = "text";
    private static final String ID = "id";


    @Override
    public String getViewComment(Comment comment) {
        var sb = new StringBuilder();
        addCommentToView(comment, sb);
        return sb.toString();
    }

    @Override
    public String getViewComments(List<Comment> comments) {
        var sb = new StringBuilder();
        for (var comment : comments) {
            addCommentToView(comment, sb);
        }
        return sb.toString();
    }

    private void addCommentToView(Comment comment, StringBuilder sb) {
        sb.append(ID)
                .append(": ")
                .append(comment.getId())
                .append(" ")
                .append(BOOK_ID)
                .append(" = ")
                .append(comment.getBook().getId())
                .append(" ")
                .append(TEXT)
                .append(" = ")
                .append(comment.getText())
                .append("\n");
    }

}
