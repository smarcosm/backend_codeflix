package com.smarcosm.admin_catalogo.application.video.retrieve.list;

import com.smarcosm.admin_catalogo.application.Fixture;
import com.smarcosm.admin_catalogo.application.UseCaseTest;
import com.smarcosm.admin_catalogo.application.genre.retrieve.list.GenreListOutput;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.video.Video;
import com.smarcosm.admin_catalogo.domain.video.VideoGateway;
import com.smarcosm.admin_catalogo.domain.video.VideoSearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class ListVideoUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultListVideoUseCase useCase;
    @Mock
    private VideoGateway videoGateway;
    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListVideo_shouldReturnVideo() {
        // given
        final var videos = List.of(Fixture.video(), Fixture.video());

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirect = "asc";
        final var expectedTotal = 2;

        final var expectedItems =  videos.stream().map(VideoListOutput::from).toList();

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, expectedTotal, videos);

        when(videoGateway.findAll(any())).thenReturn(expectedPagination);

        final var aQuery = new VideoSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirect);

        //when
        final var actualOutput = useCase.execute(aQuery);

        //then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        Mockito.verify(videoGateway, times(1)).findAll(eq(aQuery));
    }

    @Test
    public void givenAValidQuery_whenCallsListVideoAndResultIsEmpty_thenShouldReturnEmptyVideo() {
        //given
        final var videos = List.<Video>of();
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirect = "asc";
        final var expectedTotal = 0;

        final var expectedItems =  List.<GenreListOutput>of();

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, expectedTotal, videos);

        when(videoGateway.findAll(any())).thenReturn(expectedPagination);

        final var aQuery = new VideoSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirect);

        //when
        final var actualOutput = useCase.execute(aQuery);

        //then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        Mockito.verify(videoGateway, times(1)).findAll(eq(aQuery));
    }

    @Test
    public void givenAValidQuery_whenCallsListVideoAndGatewayThrowsRandomError_ShouldReturnException() {

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirect = "asc";
        final var expectedErrorMessage = "Gateway error";

        final var aQuery = new VideoSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirect);

        when(videoGateway.findAll(Mockito.eq(aQuery)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualOutput = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(aQuery));

        Assertions.assertEquals(expectedErrorMessage, actualOutput.getMessage());

        Mockito.verify(videoGateway, times(1)).findAll(eq(aQuery));


    }
}
