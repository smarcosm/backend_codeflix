package com.smarcosm.admin_catalogo.application.castmember.retrieve.list;

import com.smarcosm.admin_catalogo.domain.Fixture;
import com.smarcosm.admin_catalogo.IntegrationTest;
import com.smarcosm.admin_catalogo.domain.castmember.CastMember;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberGateway;
import com.smarcosm.admin_catalogo.domain.pagination.SearchQuery;
import com.smarcosm.admin_catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class ListCastMemberUseCaseIT {
    @Autowired
    private ListCastMembersUseCase useCase;
    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;
    @Test
    public void givenAValidQuery_whenCallsListCastMembers_ShouldReturnAll() {
        //given
        final var members = List.of(
                CastMember.newMember(Fixture.name(), Fixture.CastMembers.type()),
                CastMember.newMember(Fixture.name(), Fixture.CastMembers.type()),
                CastMember.newMember(Fixture.name(), Fixture.CastMembers.type()),
                CastMember.newMember(Fixture.name(), Fixture.CastMembers.type())
        );
        this.castMemberRepository.saveAllAndFlush(members.stream().map(CastMemberJpaEntity::from).toList());

        Assertions.assertEquals(4, this.castMemberRepository.count());

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirect = "asc";
        final var expectedTotal = 4;

        final var expectedItems =  members.stream().map(CastMemberListOutput::from).toList();


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

        verify(castMemberGateway).findAll(any());

    }

    @Test
    public void givenAValidQuery_whenCallsListCastMemberAndIsEmpty_thenShouldReturn() {
        //given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirect = "asc";
        final var expectedTotal = 0;

        final var expectedItems =  List.<CastMemberListOutput>of();


        Assertions.assertEquals(0, this.castMemberRepository.count());


        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirect);

        //when
        final var actualOutput = useCase.execute(aQuery);

        //then
        Assertions.assertEquals(expectedPage, actualOutput.currentPage());
        Assertions.assertEquals(expectedPerPage, actualOutput.perPage());
        Assertions.assertEquals(expectedTotal, actualOutput.total());
        Assertions.assertEquals(expectedItems, actualOutput.items());

        verify(castMemberGateway).findAll(any());
    }

    @Test
    public void givenAValidQuery_whenCallsListCastMemberAndGatewayThrowsRandomError_ShouldReturnException() {
        //given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var expectedErrorMessage = "Gateway error";

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(castMemberGateway).findAll(any());

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        //when
        final var actualException = Assertions.assertThrows(IllegalStateException.class, ()->{
            useCase.execute(aQuery);
        });

        //then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(castMemberGateway).findAll(any());


    }
}
