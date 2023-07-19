package com.smarcosm.admin_catalogo.application.category.retrieve.get;

import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.domain.exception.DomainException;
import com.smarcosm.admin_catalogo.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;


public class DefaultGetCategoryByIdUseCase extends  GetCategoryByIdUseCase{
   private final CategoryGateway categoryGateway;

   public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway){
       this.categoryGateway = Objects.requireNonNull(categoryGateway);
   }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var  anCategoryID = CategoryID.from(anIn);

        return this.categoryGateway.findById(anCategoryID)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(anCategoryID));
    }
    private Supplier<DomainException> notFound(final CategoryID anId){
       return () -> DomainException.with(
               new Error("Category with ID %s was not found".formatted(anId.getValue()))
       );
    }


}
