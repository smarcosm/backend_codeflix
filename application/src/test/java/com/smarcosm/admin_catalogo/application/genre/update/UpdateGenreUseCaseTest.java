package com.smarcosm.admin_catalogo.application.genre.update;

import com.smarcosm.admin_catalogo.application.genre.create.CreateGenreCommand;
import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.domain.genre.Genre;
import com.smarcosm.admin_catalogo.domain.genre.GenreGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateGenreUseCaseTest {
    @InjectMocks
    private DefaulUpdateGenreUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;
    @Mock
    private GenreGateway genreGateway;
    @Test
    public void givenAValidCommand_whenCallsUpdateGenre_shouldReturnGenreId(){
        // given
        final var aGenre = Genre.newGenre("acao", true);
        final var expectedId = aGenre.getId();

        final var expectedName= "Ação";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                expectedCategories
        );

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());
        //when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        Mockito.verify(genreGateway, times(1)).findById(eq(expectedId));

        Mockito.verify(genreGateway, times(1)).update(argThat(aUpdateGenre ->
                Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedName, aUpdateGenre.getName())
                && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                && Objects.isNull(aUpdateGenre.getDeletedAt())

        ));
    }
}
