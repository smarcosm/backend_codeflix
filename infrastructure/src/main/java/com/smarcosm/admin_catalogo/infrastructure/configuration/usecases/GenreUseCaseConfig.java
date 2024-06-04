package com.smarcosm.admin_catalogo.infrastructure.configuration.usecases;

import com.smarcosm.admin_catalogo.application.genre.create.CreateGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.create.DefaultCreateGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.delete.DefaultDeleteGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.delete.DeleteGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.retrieve.get.DefaultGetGenreByIdUseCase;
import com.smarcosm.admin_catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.smarcosm.admin_catalogo.application.genre.retrieve.list.DefaultListGenresUseCase;
import com.smarcosm.admin_catalogo.application.genre.retrieve.list.ListGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.update.DefaultUpdateGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.update.UpdateGenreUseCase;
import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import com.smarcosm.admin_catalogo.domain.genre.GenreGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class GenreUseCaseConfig {
    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;
    public GenreUseCaseConfig(CategoryGateway categoryGateway, GenreGateway genreGateway) {
        this.categoryGateway =  Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }
    @Bean
    public CreateGenreUseCase createGenreUseCase(){
        return new DefaultCreateGenreUseCase(categoryGateway, genreGateway);
    }
    @Bean
    public UpdateGenreUseCase updateGenreUseCase(){ return new DefaultUpdateGenreUseCase(categoryGateway, genreGateway); }
    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase(){ return new DefaultGetGenreByIdUseCase(genreGateway); }
    @Bean
    public ListGenreUseCase listGenreUseCase(){ return new DefaultListGenresUseCase(genreGateway); }
    @Bean
    public DeleteGenreUseCase deleteGenreUseCase(){ return new DefaultDeleteGenreUseCase( genreGateway); }
}
