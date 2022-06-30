package ru.otus.batch.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    private void addBookForViewPropertyMapper(ModelMapper mapper) {
//        var propertyMapper = mapper.createTypeMap(Book.class, BookForViewDto.class);
//        propertyMapper.addMappings(
//                m -> m.map(book -> book.getGenre().getName(), BookForViewDto::setGenreName)
//        );
//        propertyMapper.addMappings(
//                m -> m.map(book -> book.getAuthor().getName(), BookForViewDto::setAuthorName)
//        );
//    }
}

