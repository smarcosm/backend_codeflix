package com.smarcosm.admin_catalogo.application.category.update;

import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {
    public static UpdateCategoryOutput from(final Category aCategory){
        return new UpdateCategoryOutput(aCategory.getId());
    }
}
