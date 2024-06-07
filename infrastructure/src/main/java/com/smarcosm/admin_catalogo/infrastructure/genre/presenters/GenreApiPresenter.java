package com.smarcosm.admin_catalogo.infrastructure.genre.presenters;

import com.smarcosm.admin_catalogo.application.genre.retrieve.get.GenreOutput;
import com.smarcosm.admin_catalogo.application.genre.retrieve.list.GenreListOutput;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.GenreListResponse;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.GenreResponse;

public interface GenreApiPresenter {
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
    static GenreResponse present(final GenreOutput output){
        return new GenreResponse(
                output.id(),
                output.name(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deleteAt()
        );
    }
    static GenreListResponse present(final GenreListOutput output){
        return new GenreListResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.deleteAt()
        );
    }
}
