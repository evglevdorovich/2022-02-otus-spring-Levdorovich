package ru.otus.library.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.library.domain.Book;
import ru.otus.library.dto.BookForViewDto;

@Configuration
public class MapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();
        addBookForViewPropertyMapper(mapper);
        return mapper;
    }

    private void addBookForViewPropertyMapper(ModelMapper mapper) {
        var propertyMapper = mapper.createTypeMap(Book.class, BookForViewDto.class);
        propertyMapper.addMappings(
                m -> m.map(book -> book.getGenre().getName(), BookForViewDto::setGenreName)
        );
        propertyMapper.addMappings(
                m -> m.map(book -> book.getAuthor().getName(), BookForViewDto::setAuthorName)
        );
    }

}
