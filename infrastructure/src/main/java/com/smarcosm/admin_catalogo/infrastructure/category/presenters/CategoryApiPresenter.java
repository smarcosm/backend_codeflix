package com.smarcosm.admin_catalogo.infrastructure.category.presenters;

import com.smarcosm.admin_catalogo.application.category.retrieve.get.CategoryOutput;
import com.smarcosm.admin_catalogo.application.category.retrieve.list.CategoryListOutput;
import com.smarcosm.admin_catalogo.infrastructure.category.models.CategoryResponse;
import com.smarcosm.admin_catalogo.infrastructure.category.models.CategoryListResponse;

public interface CategoryApiPresenter {
//    Function<GenreOutput, GenreResponse> present =
//            output -> new GenreResponse(
//                    output.id().getValue(),
//                    output.name(),
//                    output.description(),
//                    output.isActive(),
//                    output.createdAt(),
//                    output.updatedAt(),
//                    output.deleteAt()
//            );
    static CategoryResponse present(final CategoryOutput output){
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deleteAt()
        );
    }
    static CategoryListResponse present(final CategoryListOutput output){
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deleteAt()
        );
    }
}
