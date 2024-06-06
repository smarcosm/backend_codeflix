package com.smarcosm.admin_catalogo.infrastructure.api.controllers;

import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.infrastructure.api.GenreAPI;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.CreateGenreRequest;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.GenreListReponse;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.GenreResponse;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.UpdateGenreRequest;
import org.springframework.http.ResponseEntity;

public class GenreController implements GenreAPI {
    @Override
    public ResponseEntity<?> create(final CreateGenreRequest input) {
        return null;
    }

    @Override
    public Pagination<GenreListReponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return null;
    }

    @Override
    public GenreResponse getById(final String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateGenreRequest input) {
        return null;
    }

    @Override
    public void deleteById(final String id) {

    }
}
