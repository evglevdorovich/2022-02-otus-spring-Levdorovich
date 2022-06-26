package ru.otus.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.batch.model.jpa.Author;
import ru.otus.batch.model.jpa.Book;
import ru.otus.batch.model.jpa.Comment;
import ru.otus.batch.model.jpa.Genre;
import ru.otus.batch.model.mongo.MongoAuthor;
import ru.otus.batch.model.mongo.MongoBook;
import ru.otus.batch.model.mongo.MongoComment;
import ru.otus.batch.model.mongo.MongoGenre;

import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
@ConfigurationProperties(prefix = "application.job")
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Setter
    private int pageSize;
    @Setter
    private int chunkSize;

    @Bean
    public JpaPagingItemReader<Book> bookReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookReader")
                .pageSize(pageSize)
                .entityManagerFactory(entityManagerFactory)
                .queryString("FROM Book")
                .build();
    }

    @Bean
    public JpaPagingItemReader<Author> authorReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorReader")
                .pageSize(pageSize)
                .entityManagerFactory(entityManagerFactory)
                .queryString("FROM Author")
                .build();
    }

    @Bean
    public JpaPagingItemReader<Genre> genreReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Genre>()
                .name("genreReader")
                .pageSize(pageSize)
                .entityManagerFactory(entityManagerFactory)
                .queryString("FROM Genre")
                .build();
    }

    @Bean
    public JpaPagingItemReader<Comment> commentReader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Comment>()
                .name("commentReader")
                .pageSize(pageSize)
                .entityManagerFactory(entityManagerFactory)
                .queryString("FROM Comment")
                .build();
    }

    @Bean
    public ItemProcessor<Book, MongoBook> bookProcessor(ModelMapper mapper) {
        return (book) -> mapper.map(book, MongoBook.class);
    }

    @Bean
    public ItemProcessor<Author, MongoAuthor> authorProcessor(ModelMapper mapper) {
        return (author) -> mapper.map(author, MongoAuthor.class);
    }

    @Bean
    public ItemProcessor<Genre, MongoGenre> genreProcessor(ModelMapper mapper) {
        return (genre) -> mapper.map(genre, MongoGenre.class);
    }

    @Bean
    public ItemProcessor<Comment, MongoComment> commentProcessor(ModelMapper mapper) {
        return (comment) -> mapper.map(comment, MongoComment.class);
    }

    @Bean
    public MongoItemWriter<MongoBook> bookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoBook>()
                .collection("books")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public MongoItemWriter<MongoAuthor> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoAuthor>()
                .collection("authors")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public MongoItemWriter<MongoGenre> genreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoGenre>()
                .collection("genres")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public MongoItemWriter<MongoComment> commentWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<MongoComment>()
                .collection("comments")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Job jpaToMongoJob(Step bookJpaToMongoStep, Step authorJpaToMongoStep,
                             Step genreJpaToMongoStep, Step commentJpaToMongoStep) {
        return jobBuilderFactory.get("jpaToMongoJob")
                .flow(bookJpaToMongoStep)
                .next(authorJpaToMongoStep)
                .next(genreJpaToMongoStep)
                .next(commentJpaToMongoStep)
                .end()
                .build();
    }

    @Bean
    public Step bookJpaToMongoStep(ItemReader<Book> bookReader, ItemWriter<MongoBook> bookWriter,
                                   ItemProcessor<Book, MongoBook> bookProcessor) {
        return stepBuilderFactory.get("bookStep")
                .<Book, MongoBook>chunk(chunkSize)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public Step authorJpaToMongoStep(ItemReader<Author> authorReader, ItemWriter<MongoAuthor> authorWriter,
                                     ItemProcessor<Author, MongoAuthor> authorProcessor) {
        return stepBuilderFactory.get("authorStep")
                .<Author, MongoAuthor>chunk(chunkSize)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }

    @Bean
    public Step genreJpaToMongoStep(ItemReader<Genre> genreReader, ItemWriter<MongoGenre> genreWriter,
                                    ItemProcessor<Genre, MongoGenre> genreProcessor) {
        return stepBuilderFactory.get("genreStep")
                .<Genre, MongoGenre>chunk(chunkSize)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }

    @Bean
    public Step commentJpaToMongoStep(ItemReader<Comment> commentReader, ItemWriter<MongoComment> commentWriter,
                                      ItemProcessor<Comment, MongoComment> commentProcessor) {
        return stepBuilderFactory.get("commentStep")
                .<Comment, MongoComment>chunk(chunkSize)
                .reader(commentReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .build();
    }
}
