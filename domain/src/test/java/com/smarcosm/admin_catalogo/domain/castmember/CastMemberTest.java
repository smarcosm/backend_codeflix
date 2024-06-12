package com.smarcosm.admin_catalogo.domain.castmember;

import com.smarcosm.admin_catalogo.domain.exception.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CastMemberTest {

    @Test
    public void givenAValidParams_whenCallsNewMember_thenInstantiateACastMember(){
        final var expectedName = "Vin Diesel";
        final var expectedType = CastMemberType.ACTOR;

        final var actualMember = CastMember.newMember(expectedName, expectedType);

        Assertions.assertNotNull(actualMember);
        Assertions.assertNotNull(actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());

        Assertions.assertNotNull(actualMember.getCreatedAt());
        Assertions.assertNotNull(actualMember.getUpdatedAt());
        Assertions.assertEquals(actualMember.getCreatedAt(), actualMember.getUpdatedAt());
    }
    @Test
    public void givenAnInvalidNullName_whenCallNewMember_shouldReceiveNotification() {
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());


    }
    @Test
    public void givenAnInvalidEmptyName_whenCallNewMember_shouldReceiveNotification() {
        final var expectedName = " ";
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }
    @Test
    public void givenAnInvalidNameWithLengthMore255_whenCallNewMember_shouldReceiveNotification() {
        final String expectedName ="""
                        O incentivo ao avanço tecnológico, assim como o entendimento das metas propostas não pode mais se dissociar das formas de ação.
                        Do mesmo modo, a valorização de fatores subjetivos garante a contribuição de um grupo importante na determinação das novas proposições.
                        O que temos que ter sempre em mente é que o início da atividade geral de formação de atitudes agrega valor ao estabelecimento das condições financeiras e administrativas exigidas.
                                """;
        final var expectedType = CastMemberType.ACTOR;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }
    @Test
    public void givenAnInvalidNullType_whenCallNewMember_shouldReceiveNotification() {
        final var expectedName = "Vin Diesel";
        final CastMemberType expectedType = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }
}
