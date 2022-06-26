package ru.otus.library.mongock.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import ru.otus.library.domain.MongoAuthor;
import ru.otus.library.domain.MongoBook;
import ru.otus.library.domain.MongoComment;
import ru.otus.library.domain.MongoGenre;
import ru.otus.library.domain.ShortBook;
import ru.otus.library.repository.CommentRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private MongoAuthor firstAuthor;
    private MongoAuthor secondAuthor;
    private MongoAuthor thirdAuthor;
    private MongoAuthor fourthAuthor;
    private MongoAuthor fifthAuthor;

    private MongoGenre firstGenre;
    private MongoGenre secondGenre;
    private MongoGenre thirdGenre;
    private MongoGenre fourthGenre;
    private MongoGenre fifthGenre;

    private MongoBook firstBook;
    private MongoBook secondBook;
    private MongoBook thirdBook;
    private MongoBook fourthBook;
    private MongoBook fifthBook;

    @ChangeSet(order = "000", id = "dropDB", author = "jack", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "jack", runAlways = true)
    public void initAuthors(MongockTemplate template) {
        firstAuthor = template.save(new MongoAuthor("Harper Lee"));
        secondAuthor = template.save(new MongoAuthor("Velma Walls"));
        thirdAuthor = template.save(new MongoAuthor("John Grisham"));
        fourthAuthor = template.save(new MongoAuthor("Joan Didlon"));
        fifthAuthor = template.save(new MongoAuthor("Ken Kesey"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "jack", runAlways = true)
    public void initStudents(MongockTemplate template) {
        firstGenre = template.save(new MongoGenre("myth"));
        secondGenre = template.save(new MongoGenre("romance"));
        thirdGenre = template.save(new MongoGenre("novel"));
        fourthGenre = template.save(new MongoGenre("fiction"));
        fifthGenre = template.save(new MongoGenre("drama"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "jack", runAlways = true)
    public void initBooks(MongockTemplate template) {
        firstBook = template.save(new MongoBook("December 1941", firstAuthor, firstGenre));
        secondBook = template.save(new MongoBook("1984", secondAuthor, secondGenre));
        thirdBook = template.save(new MongoBook("11-22-63", thirdAuthor, thirdGenre));
        fourthBook = template.save(new MongoBook("Gallery", fourthAuthor, fourthGenre));
        fifthBook = template.save(new MongoBook("Da Vinci", fifthAuthor, fifthGenre));
    }

    @ChangeSet(order = "004", id = "initComments", author = "jack", runAlways = true)
    public void initComments(CommentRepository repository) {

        repository.insert(new MongoComment(
               new ShortBook(firstBook.getId()), "bad")).subscribe();
        repository.insert(new MongoComment(
                new ShortBook(firstBook.getId()), "good")).subscribe();
        repository.insert(new MongoComment(
                new ShortBook(secondBook.getId()), "very bad")).subscribe();
        repository.insert(new MongoComment(
                new ShortBook(thirdBook.getId()), "very good")).subscribe();
        repository.insert(new MongoComment(
                new ShortBook(fourthBook.getId()), "excellent")).subscribe();
        repository.insert(new MongoComment(
                new ShortBook(fifthBook.getId()), "amazing")).subscribe();
    }

    @ChangeSet(order = "005", id = "addIndexes", author = "jack", runAlways = true)
    public void addIndexes(MongoDatabase db) {
        var uniqueOptions = new IndexOptions().unique(true);
        db.getCollection("genres").createIndex(Indexes.ascending("name"), uniqueOptions);
        db.getCollection("authors").createIndex(Indexes.ascending("name"), uniqueOptions);
        db.getCollection("books").createIndex(Indexes.ascending("name"));
    }
}
