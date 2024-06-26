package com.smarcosm.admin_catalogo.application.castmember.retrieve.get;

import com.smarcosm.admin_catalogo.application.Fixture;
import com.smarcosm.admin_catalogo.application.UseCaseTest;
import com.smarcosm.admin_catalogo.domain.castmember.CastMember;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberGateway;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberID;
import com.smarcosm.admin_catalogo.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class GetCastMemberByIdUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultGetCastMemberByIdUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }
    @Test
    public void givenAValidId_whenCallsGetCastMember_shouldReturnIt() {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var aMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aMember.getId();

        when(castMemberGateway.findById(any()))
                .thenReturn(Optional.of(aMember));

        // when
        final var actualOutput = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());
        Assertions.assertEquals(expectedName, actualOutput.name());
        Assertions.assertEquals(expectedType, aMember.getType());
        Assertions.assertEquals(actualOutput.createdAt(), aMember.getCreatedAt());
        Assertions.assertEquals(actualOutput.updatedAt(), aMember.getUpdatedAt());

        verify(castMemberGateway).findById(expectedId);

    }
    @Test
    public void givenAInvalidId_whenCallsGetCastMemberAndDosNotFound_shouldReturnNotFoundException() {
        // given
        final var expectedId = CastMemberID.from("123");
        final var expectedMessage = "CastMembers with ID 123 was not found";
        when(castMemberGateway.findById(any()))
                .thenReturn(Optional.empty());

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
