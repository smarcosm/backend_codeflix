package com.smarcosm.admin_catalogo.application.genre.retrieve.get;

import com.smarcosm.admin_catalogo.domain.exception.NotFoundException;
import com.smarcosm.admin_catalogo.domain.genre.Genre;
import com.smarcosm.admin_catalogo.domain.genre.GenreGateway;
import com.smarcosm.admin_catalogo.domain.genre.GenreID;

import java.util.Objects;


public class DefaultGetGenreByIdUseCase extends GetGenreByIdUseCase {
   private final GenreGateway genreGateway;

   public DefaultGetGenreByIdUseCase(final GenreGateway genreGateway){
       this.genreGateway = Objects.requireNonNull(genreGateway);
   }

    @Override
    public GenreOutput execute(final String anIn) {
        final var  anGenreId = GenreID.from(anIn);

        return this.genreGateway.findById(GenreID.from(anIn))
                .map(GenreOutput::from)
                .orElseThrow(() -> NotFoundException.with(Genre.class, anGenreId));
    }

}
