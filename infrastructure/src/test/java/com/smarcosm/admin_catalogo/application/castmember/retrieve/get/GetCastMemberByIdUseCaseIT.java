package com.smarcosm.admin_catalogo.application.castmember.retrieve.get;

import com.smarcosm.admin_catalogo.Fixture;
import com.smarcosm.admin_catalogo.IntegrationTest;
import com.smarcosm.admin_catalogo.domain.castmember.CastMember;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberGateway;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberID;
import com.smarcosm.admin_catalogo.domain.exception.NotFoundException;
import com.smarcosm.admin_catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@IntegrationTest
public class GetCastMemberByIdUseCaseIT {
    @Autowired
    private GetCastMemberByIdUseCase useCase;
    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Test
    public void givenAValidId_whenCallsGetCastMember_shouldReturnIt() {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();

        final var aMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aMember.getId();

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, this.castMemberRepository.count());
        // when
        final var actualOutput = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());
        Assertions.assertEquals(expectedName, actualOutput.name());
        Assertions.assertEquals(expectedType, aMember.getType());
        Assertions.assertEquals(actualOutput.createdAt(), aMember.getCreatedAt());
        Assertions.assertEquals(actualOutput.updatedAt(), aMember.getUpdatedAt());

        verify(castMemberGateway).findById(any());

    }
    @Test
    public void givenAInvalidId_whenCallsGetCastMemberAndDosNotFound_shouldReturnNotFoundException() {
        // given
        final var expectedId = CastMemberID.from("123");
        final var expectedMessage = "CastMember with ID 123 was not found";


        // when
        final var actualOutput = Assertions.assertThrows(NotFoundException.class, ()-> {
            useCase.execute(expectedId.getValue());
        });

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedMessage, actualOutput.getMessage());


        verify(castMemberGateway).findById(expectedId);

    }


}
