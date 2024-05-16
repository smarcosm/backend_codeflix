package com.smarcosm.admin_catalogo.application.genre.delete;

import com.smarcosm.admin_catalogo.application.UseCaseTest;
import com.smarcosm.admin_catalogo.domain.genre.Genre;
import com.smarcosm.admin_catalogo.domain.genre.GenreGateway;
import com.smarcosm.admin_catalogo.domain.genre.GenreID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteGenreyUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultDeleteGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }
    @Test
    public void givenAValidId_whenCallsDeleteGenre_shouldDeleteGenre(){
        // given
        final var aGenre = Genre.newGenre("A", true);
        final var expectedId = aGenre.getId();

        doNothing().when(genreGateway).deleteById(any());

        //when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(genreGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteGenre_shouldBeOK(){
        //given
        final var expectedId = GenreID.from("123");

        doNothing().when(genreGateway).deleteById(any());

        //when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Mockito.verify(genreGateway, times(1)).deleteById(eq(expectedId));
    }
    @Test
    public void givenAValidGenreId_whenCallsDeleteGenreAndGatewayThrowsUnexpectedError_shouldReceiveException(){
        //given
        final var expectedId = GenreID.from("123");

        doThrow(new IllegalStateException("Gateway error"))
                .when(genreGateway).deleteById(any());

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(genreGateway, times(1)).deleteById(eq(expectedId));
    }

}
