package com.smarcosm.admin_catalogo.infrastructure.api.controllers;

import com.smarcosm.admin_catalogo.application.category.create.CreateCategoryCommand;
import com.smarcosm.admin_catalogo.application.category.create.CreateCategoryOutput;
import com.smarcosm.admin_catalogo.application.category.create.CreateCategoryUseCase;
import com.smarcosm.admin_catalogo.application.category.delete.DeleteCategoryUseCase;
import com.smarcosm.admin_catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.smarcosm.admin_catalogo.application.category.update.UpdateCategoryCommand;
import com.smarcosm.admin_catalogo.application.category.update.UpdateCategoryOutput;
import com.smarcosm.admin_catalogo.application.category.update.UpdateCategoryUseCase;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.validation.handler.Notification;
import com.smarcosm.admin_catalogo.infrastructure.api.CategoryAPI;
import com.smarcosm.admin_catalogo.infrastructure.category.models.CategoryApiOutput;
import com.smarcosm.admin_catalogo.infrastructure.category.models.CreateCategoryApiInput;
import com.smarcosm.admin_catalogo.infrastructure.category.models.UpdateCategoryApiInput;
import com.smarcosm.admin_catalogo.infrastructure.category.presenters.CategoryApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    public CategoryController(final CreateCategoryUseCase createCategoryUseCase,
                              final GetCategoryByIdUseCase getCategoryByIdUseCase,
                              final UpdateCategoryUseCase updateCategoryUseCase,
                              final DeleteCategoryUseCase deleteCategoryUseCase
    ) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryApiInput input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );
        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listCategory(String search, int page, int perPage, String sort, String direction) {
        return null;
    }

    @Override
    public CategoryApiOutput getById(String id) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryApiInput input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );
        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String anId) {
    this.deleteCategoryUseCase.execute(anId);
    }
}
