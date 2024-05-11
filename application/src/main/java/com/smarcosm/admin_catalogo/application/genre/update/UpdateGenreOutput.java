package com.smarcosm.admin_catalogo.application.genre.update;

import com.smarcosm.admin_catalogo.domain.genre.Genre;

public record UpdateGenreOutput(
        String id
) {
    public static UpdateGenreOutput from(final String anId){
        return new UpdateGenreOutput(anId);

    }
    public static UpdateGenreOutput from(final Genre aGenre){
        return new UpdateGenreOutput(aGenre.getId().getValue());

    }
}
