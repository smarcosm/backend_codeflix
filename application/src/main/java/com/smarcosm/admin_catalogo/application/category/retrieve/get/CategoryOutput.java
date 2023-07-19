package com.smarcosm.admin_catalogo.application.category.retrieve.get;

import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;

import java.time.Instant;

public record CategoryOutput (
        CategoryID id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deleteAt
){
    public  static  CategoryOutput from(final Category aCategory){
        return new CategoryOutput(
                aCategory.getId(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeletedAt()
        );
    }
}
