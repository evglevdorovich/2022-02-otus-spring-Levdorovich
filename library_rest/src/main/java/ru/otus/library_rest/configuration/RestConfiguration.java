package ru.otus.library_rest.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import ru.otus.library_rest.projections.BookWithAuthorAndGenreProjection;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config
                .getProjectionConfiguration()
                .addProjection(BookWithAuthorAndGenreProjection.class);
    }
}
