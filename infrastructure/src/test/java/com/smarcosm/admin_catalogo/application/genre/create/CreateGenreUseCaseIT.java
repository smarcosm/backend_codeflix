package com.smarcosm.admin_catalogo.application.genre.create;

import com.smarcosm.admin_catalogo.IntegrationTest;
import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.domain.genre.GenreGateway;
import com.smarcosm.admin_catalogo.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

@IntegrationTest
public class CreateGenreUseCaseIT {
    @Autowired
    private DefaultCreateGenreUseCase useCase;
    @SpyBean
    private CategoryGateway categoryGateway;
    @SpyBean
    private GenreGateway genreGateway;
    @Autowired
    private GenreRepository genreRepository;
     @Test
    public void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId(){
        // given
         final var filmes = categoryGateway.create(Category.newCategory("Filmes", null, true));
        final var expectedName= "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes.getId());

        final var aCommand = CreateGenreCommand.with(expectedName, expectedIsActive, asString(expectedCategories));

        //when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());


        final var actualGenre = genreRepository.findById(actualOutput.id()).get();
        Assertions.assertEquals(expectedName, actualGenre.getName());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(expectedCategories.size() == actualGenre.getCategoryIDs().size()
                && expectedCategories.containsAll(actualGenre.getCategoryIDs())
        );
        Assertions.assertNotNull(actualGenre.getCreatedAt());
        Assertions.assertNotNull(actualGenre.getUpdatedAt());
        Assertions.assertNull(actualGenre.getDeletedAt());

    }

    private List<String> asString(final List<CategoryID> categories){
        return categories.stream().map(CategoryID::getValue).toList();
    }
}

