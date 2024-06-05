package com.smarcosm.admin_catalogo.application.genre.retrieve.list;

import com.smarcosm.admin_catalogo.IntegrationTest;
import com.smarcosm.admin_catalogo.domain.genre.Genre;
import com.smarcosm.admin_catalogo.domain.genre.GenreGateway;
import com.smarcosm.admin_catalogo.domain.pagination.SearchQuery;
import com.smarcosm.admin_catalogo.infrastructure.genre.persistence.GenreJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@IntegrationTest
public class ListGenreUseCaseIT {
    @Autowired
    private ListGenreUseCase useCase;
    @Autowired
    private GenreGateway genreGateway;
    @Autowired
    private GenreRepository genreRepository;
    @Test
    public void givenAValidQuery_whenCallsListGenre_ShouldReturnGenres() {
        //given
        final var genres = List.of(
                Genre.newGenre("Ação", true),
                Genre.newGenre("Aventura",  true),
                Genre.newGenre("Drama",  true)
        );
        genreRepository.saveAllAndFlush(
                genres.stream().map(GenreJpaEntity::from)
                        .toList()
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirect = "asc";
        final var expectedTotal = 3;

        final var expectedItems =  genres.stream().map(GenreListOutput::from).toList();

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirect);

        //when
        final var actualOutput = useCase.execute(aQuery);

        //then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertTrue(
                expectedItems.size() == actualOutput.items().size()
                && expectedItems.containsAll(actualOutput.items())
        );


    }

    @Test
    public void givenAValidQuery_whenCallsListGenreAndResultIsEmpty_thenShouldReturnEmptyGenre() {
        //given
        final var genres = List.<Genre>of();
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "A";
        final var expectedSort = "createdAt";
        final var expectedDirect = "asc";
        final var expectedTotal = 0;

        final var expectedItems =  List.<GenreListOutput>of();


        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirect);

        //when
        final var actualOutput = useCase.execute(aQuery);

        //then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

    }

}
