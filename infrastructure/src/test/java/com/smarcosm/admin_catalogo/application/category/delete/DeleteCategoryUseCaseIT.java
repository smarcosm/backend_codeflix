package com.smarcosm.admin_catalogo.application.category.delete;

import com.smarcosm.admin_catalogo.IntegrationTest;
import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.infrastructure.category.persitence.CategoryJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.category.persitence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@IntegrationTest
public class DeleteCategoryUseCaseIT {
    @Autowired
    private DeleteCategoryUseCase useCase;
    @Autowired
    private CategoryRepository categoryRepository;
    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK(){
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

       save(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Assertions.assertEquals(0, categoryRepository.count());

    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOK(){

        final var expectedId = CategoryID.from("123");

        Assertions.assertEquals(0, categoryRepository.count());
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Assertions.assertEquals(0, categoryRepository.count()); }
    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException(){
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    private void save(final Category... aCategory) {
        categoryRepository.saveAllAndFlush(
                Arrays.stream(aCategory)
                        .map(CategoryJpaEntity::from)
                        .toList()
        );
    }

}
