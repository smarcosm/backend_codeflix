package com.smarcosm.admin_catalogo.application.category.delete;

import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase{
    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }


    @Override
    public void execute(final String anIn){
        this.categoryGateway.deleteById(CategoryID.from(anIn));
    }
}
