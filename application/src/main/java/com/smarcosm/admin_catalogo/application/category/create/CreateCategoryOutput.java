package com.smarcosm.admin_catalogo.application.category.create;

import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {
    public static CreateCategoryOutput from(final CategoryID anId){
        return new CreateCategoryOutput(anId);

    }
    public static CreateCategoryOutput from(final Category aCategory){
        return new CreateCategoryOutput(aCategory.getId());

    }
}
