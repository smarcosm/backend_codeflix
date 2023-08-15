package com.smarcosm.admin_catalogo.application;

import com.smarcosm.admin_catalogo.IntegrationTest;
import com.smarcosm.admin_catalogo.application.category.create.CreateCategoryUseCase;
import com.smarcosm.admin_catalogo.infrastructure.category.persitence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleIT {
    @Autowired
    private CreateCategoryUseCase useCase;
    @Autowired
    private CategoryRepository repository;
    @Test
    public void testInjects(){
        Assertions.assertNotNull(useCase);
        Assertions.assertNotNull(repository);
    }
}
