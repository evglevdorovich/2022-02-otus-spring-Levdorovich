package ru.otus.library.mongock.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.repository.CommentRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author firstAuthor;
    private Author secondAuthor;
    private Author thirdAuthor;
    private Author fourthAuthor;
    private Author fifthAuthor;

    private Genre firstGenre;
    private Genre secondGenre;
    private Genre thirdGenre;
    private Genre fourthGenre;
    private Genre fifthGenre;

    private Book firstBook;
    private Book secondBook;
    private Book thirdBook;
    private Book fourthBook;
    private Book fifthBook;

    @ChangeSet(order = "000", id = "dropDB", author = "jack", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "jack", runAlways = true)
    public void initAuthors(MongockTemplate template) {
        firstAuthor = template.save(new Author("Harper Lee"));
        secondAuthor = template.save(new Author("Velma Walls"));
        thirdAuthor = template.save(new Author("John Grisham"));
        fourthAuthor = template.save(new Author("Joan Didlon"));
        fifthAuthor = template.save(new Author("Ken Kesey"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "jack", runAlways = true)
    public void initStudents(MongockTemplate template) {
        firstGenre = template.save(new Genre("myth"));
        secondGenre = template.save(new Genre("romance"));
        thirdGenre = template.save(new Genre("novel"));
        fourthGenre = template.save(new Genre("fiction"));
        fifthGenre = template.save(new Genre("drama"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "jack", runAlways = true)
    public void initBooks(MongockTemplate template) {
        firstBook = template.save(new Book("December 1941", firstAuthor, firstGenre));
        secondBook = template.save(new Book("1984", secondAuthor, secondGenre));
        thirdBook = template.save(new Book("11-22-63", thirdAuthor, thirdGenre));
        fourthBook = template.save(new Book("Gallery", fourthAuthor, fourthGenre));
        fifthBook = template.save(new Book("Da Vinci", fifthAuthor, fifthGenre));
    }

    @ChangeSet(order = "004", id = "initComments", author = "jack", runAlways = true)
    public void initComments(CommentRepository repository) {

        repository.saveComment(new Comment(
                new Book(firstBook.getId(), firstBook.getName(), null, null), "bad")).subscribe();
        repository.saveComment(new Comment(
                new Book(firstBook.getId(), firstBook.getName(), null, null), "good")).subscribe();
        repository.saveComment(new Comment(
                new Book(secondBook.getId(), secondBook.getName(), null, null), "very bad")).subscribe();
        repository.saveComment(new Comment(
                new Book(thirdBook.getId(), thirdBook.getName(), null, null), "very good")).subscribe();
        repository.saveComment(new Comment(
                new Book(fourthBook.getId(), fourthBook.getName(), null, null), "excellent")).subscribe();
        repository.saveComment(new Comment(
                new Book(fifthBook.getId(), fifthBook.getName(), null, null), "amazing")).subscribe();
    }

    @ChangeSet(order = "005", id = "addIndexes", author = "jack", runAlways = true)
    public void addIndexes(MongoDatabase db) {
        var uniqueOptions = new IndexOptions().unique(true);
        db.getCollection("genres").createIndex(Indexes.ascending("name"), uniqueOptions);
        db.getCollection("authors").createIndex(Indexes.ascending("name"), uniqueOptions);
        db.getCollection("books").createIndex(Indexes.ascending("name"));
    }
}
