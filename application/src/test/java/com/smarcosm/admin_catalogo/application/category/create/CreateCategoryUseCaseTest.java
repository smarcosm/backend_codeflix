package com.smarcosm.admin_catalogo.application.category.create;

import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

public class CreateCategoryUseCaseTest {

    // 1. Teste do caminho feliz
    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

       final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
       Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg());

       final var useCase = new CreateCategoryUseCase(categoryGateway);

      final var actulOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actulOutput);
        Assertions.assertNotNull(actulOutput.getId());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(Mockito.argThat(aCategory -> {
                 return    Objects.equals(expectedName, aCategory.getName())
                            &&  Objects.equals(expectedDescription, aCategory.getDescription())
                            && Objects.equals(expectedIsActive, aCategory.isActive())
                            && Objects.nonNull(aCategory.getId())
                            && Objects.nonNull(aCategory.getCreatedAt())
                            && Objects.nonNull(aCategory.getUpdatedAt())
                            && Objects.isNull(aCategory.getDeletedAt());
                }

        ));
    }
    // 2. Teste passando um propriedade inválida (name)
    // 3. Teste criando uma categoria inativa
    // 4. Teste simulando um erro generico vindo do gateway
}
