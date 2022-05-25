package ru.otus.library.mongock.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import ru.otus.library.model.Author;
import ru.otus.library.model.Book;
import ru.otus.library.model.Comment;
import ru.otus.library.model.Genre;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.repository.GenreRepository;

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
    public void initAuthors(AuthorRepository repository) {
        firstAuthor = repository.save(new Author("Harper Lee"));
        secondAuthor = repository.save(new Author("Velma Walls"));
        thirdAuthor = repository.save(new Author("John Grisham"));
        fourthAuthor = repository.save(new Author("Joan Didlon"));
        fifthAuthor = repository.save(new Author("Ken Kesey"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "jack", runAlways = true)
    public void initStudents(GenreRepository repository) {
        firstGenre = repository.save(new Genre("myth"));
        secondGenre = repository.save(new Genre("romance"));
        thirdGenre = repository.save(new Genre("novel"));
        fourthGenre = repository.save(new Genre("fiction"));
        fifthGenre = repository.save(new Genre("drama"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "jack", runAlways = true)
    public void initBooks(BookRepository repository) {
        firstBook = repository.save(new Book("December 1941", firstAuthor, firstGenre));
        secondBook = repository.save(new Book("The Inevitable", secondAuthor, secondGenre));
        thirdBook = repository.save(new Book("UpStream", thirdAuthor, thirdGenre));
        fourthBook = repository.save(new Book("Dolly", fourthAuthor, fourthGenre));
        fifthBook = repository.save(new Book("Gemina", fifthAuthor, fifthGenre));
    }

    @ChangeSet(order = "004", id = "initComments", author = "jack", runAlways = true)
    public void initComments(CommentRepository repository) {
        var partialFirstBook = new Book(firstBook.getId(),firstBook.getName(),null,null);
        var partialSecondBook = new Book(secondBook.getId(),secondBook.getName(),null,null);
        var partialThirdBook = new Book(thirdBook.getId(),thirdBook.getName(),null,null);
        var partialFourthBook = new Book(fourthBook.getId(),fourthBook.getName(),null,null);
        var partialFifthBook = new Book(fifthBook.getId(),fifthBook.getName(),null,null);

        repository.saveComment(new Comment(partialFirstBook, "bad"));
        repository.saveComment(new Comment(partialFirstBook, "good"));
        repository.saveComment(new Comment(partialSecondBook, "very bad"));
        repository.saveComment(new Comment(partialThirdBook, "very good"));
        repository.saveComment(new Comment(partialFourthBook, "amazing"));
        repository.saveComment(new Comment(partialFifthBook, "excellent"));
    }

    @ChangeSet(order = "005", id = "addIndexes", author = "jack", runAlways = true)
    public void addIndexes(MongoDatabase db) {
        var uniqueOptions = new IndexOptions().unique(true);
        db.getCollection("genres").createIndex(Indexes.ascending("name"), uniqueOptions);
        db.getCollection("authors").createIndex(Indexes.ascending("name"), uniqueOptions);
        db.getCollection("books").createIndex(Indexes.ascending("name"));
    }
}
