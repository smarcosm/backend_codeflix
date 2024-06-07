package com.smarcosm.admin_catalogo.infrastructure.api.controllers;

import com.smarcosm.admin_catalogo.application.genre.create.CreateGenreCommand;
import com.smarcosm.admin_catalogo.application.genre.create.CreateGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.delete.DeleteGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.smarcosm.admin_catalogo.application.genre.retrieve.list.ListGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.update.UpdateGenreCommand;
import com.smarcosm.admin_catalogo.application.genre.update.UpdateGenreUseCase;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.pagination.SearchQuery;
import com.smarcosm.admin_catalogo.infrastructure.api.GenreAPI;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.CreateGenreRequest;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.GenreListResponse;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.GenreResponse;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.UpdateGenreRequest;
import com.smarcosm.admin_catalogo.infrastructure.genre.presenters.GenreApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
@RestController
public class GenreController implements GenreAPI {
    private final CreateGenreUseCase createGenreUseCase;
    private final GetGenreByIdUseCase getGenreByIdUseCase;
    private final DeleteGenreUseCase deleteGenreUseCase;
    private final UpdateGenreUseCase updateGenreUseCase;
    private final ListGenreUseCase listGenreUseCase;

    public GenreController(
            final CreateGenreUseCase createGenreUseCase,
            final GetGenreByIdUseCase getGenreByIdUseCase,
            final UpdateGenreUseCase updateGenreUseCase,
            final DeleteGenreUseCase deleteGenreUseCase,
            final ListGenreUseCase listGenreUseCase) {
        this.createGenreUseCase = createGenreUseCase;
        this.getGenreByIdUseCase = getGenreByIdUseCase;
        this.updateGenreUseCase = updateGenreUseCase;
        this.deleteGenreUseCase = deleteGenreUseCase;
        this.listGenreUseCase = listGenreUseCase;
    }

    @Override
    public ResponseEntity<?> create(final CreateGenreRequest input) {
        final var aCommand = CreateGenreCommand.with(
                input.name(),
                input.isActive(),
                input.categories()

        );

        final var output = this.createGenreUseCase.execute(aCommand);
        return ResponseEntity.created(URI.create("/genres/" + output.id())).body(output);
    }

    @Override
    public Pagination<GenreListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listGenreUseCase.execute(new SearchQuery(page, perPage, search,sort, direction))
                .map(GenreApiPresenter::present);
    }

    @Override
    public GenreResponse getById(final String id) {

        return GenreApiPresenter.present(this.getGenreByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateGenreRequest input) {
        final var aCommand = UpdateGenreCommand.with(
                id,
                input.name(),
                input.isActive(),
                input.categories()

        );

        final var output = this.updateGenreUseCase.execute(aCommand);
        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteById(final String id) {
        this.deleteGenreUseCase.execute(id);
    }
}
