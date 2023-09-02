package com.smarcosm.admin_catalogo.application.category.retrieve.list;

import com.smarcosm.admin_catalogo.IntegrationTest;
import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.category.CategorySearchQuery;
import com.smarcosm.admin_catalogo.infrastructure.category.persitence.CategoryJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.category.persitence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

@IntegrationTest
public class ListCategoryUseCaseIT {
    @Autowired
    private ListCategoriesUseCase useCase;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void mockUp(){
      final var categories = Stream.of(
            Category.newCategory("Filmes", null, true),
            Category.newCategory("Netflix Originals", "Títulos de autoria da Netflix", true),
            Category.newCategory("Amazon Originals", "Títulos de autoria da Amazon", true),
            Category.newCategory("Documentarios", null, true),
            Category.newCategory("Sports", null, true),
            Category.newCategory("Kids", "Categorias para crianças", true),
            Category.newCategory("Series", null, true)
      ).map(CategoryJpaEntity::from).toList();

      categoryRepository.saveAllAndFlush(categories);
    }
    @Test
    public void givenAValidTerm_whenTermDoesntMatchsPrePersisted_shouldReturnEmptyPage(){
        final var expectedPage =0;
        final var expectedPerPage =10;
        final var expectedTerms = "ladjlçsalaf";
        final var expectedShort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 0;
        final var expectedTotal = 0;

        final var aQuery = new CategorySearchQuery(
                expectedPage, expectedPerPage, expectedTerms, expectedShort, expectedDirection
        );
        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
    }
    @ParameterizedTest
    @CsvSource({
        "fil,0,10,1,1,Filmes",
        "net,0,10,1,1,Netflix Originals",
        "ZON,0,10,1,1,Amazon Originals",
        "KI,0,10,1,1,Kids",
        "crianças,0,10,1,1,Kids",
        "da Amazon,0,10,1,1,Amazon Originals",

    })
    public void givenAValidTerm_whenCallsListCategories_shouldReturnCategoriesFiltered(
            final String expectedTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedCategoryName
    ){
        final var expectedShort = "name";
        final var expectedDirection = "asc";

        final var aQuery = new CategorySearchQuery(
                expectedPage, expectedPerPage, expectedTerms, expectedShort, expectedDirection
        );
        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedCategoryName, actualResult.items().get(0).name());

    }
    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,7,7,Amazon Originals",
            "name,desc,0,10,7,7,Sports",
            "createdAt,asc,0,10,7,7,Filmes",
            "createdAt,desc,0,10,7,7,Series",
    })
    public void givenAValidSortAndDirection_whenCallsListCategories_thenShouldReturnCategoriesOrder(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedCategoryName
    ){
        final var expectedTerms = "";


        final var aQuery = new CategorySearchQuery(
                expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection
        );
        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedCategoryName, actualResult.items().get(0).name());
    }
    @ParameterizedTest
    @CsvSource({
            "0,2,2,7,Amazon Originals;Documentarios",
            "1,2,2,7,Filmes;Kids",
            "2,2,2,7,Netflix Originals;Series",
            "3,2,1,7,Sports",
    })
    public void givenAValidPage_whenCallsListCategories_shouldReturnCategoriesPainated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedCategoriesName
    ){
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTerms = "";

        final var aQuery = new CategorySearchQuery(
                expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection
        );
        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());

        int index = 0;
        for (final String expectedName : expectedCategoriesName.split(";")) {
            final String actualName = actualResult.items().get(index).name();
            Assertions.assertEquals(expectedName, actualName);
            index++;
        }


    }
}
