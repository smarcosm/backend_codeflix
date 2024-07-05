package com.smarcosm.admin_catalogo.application.video.delete;

import com.smarcosm.admin_catalogo.application.UseCaseTest;
import com.smarcosm.admin_catalogo.domain.exception.InternalErrorException;
import com.smarcosm.admin_catalogo.domain.video.VideoGateway;
import com.smarcosm.admin_catalogo.domain.video.VideoID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

public class DeleteVideoUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultDeleteVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;
    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteVideo_shouldDeleteIt(){
        // given
        final var expectedId = VideoID.unique();

        doNothing().when(videoGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }
    @Test
    public void givenAnInvalidId_whenCallsDeleteVideo_shouldBeOk(){
        // given
        final var expectedId = VideoID.from("123");

        doNothing().when(videoGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }
    @Test
    public void givenAnInvalidId_whenCallsDeleteVideoAndGatewayThrowsException_shouldReceiveException(){
        // given
        final var expectedId = VideoID.from("123");

        doThrow(InternalErrorException.with("Error on delete video", new RuntimeException()))
                .when(videoGateway).deleteById(any());

        // when
        Assertions.assertThrows(InternalErrorException.class, () -> this.useCase.execute(expectedId.getValue()));

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }

}
