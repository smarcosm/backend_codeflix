package com.smarcosm.admin_catalogo.infrastructure.castmember;

import com.smarcosm.admin_catalogo.MySQLGatewayTest;
import com.smarcosm.admin_catalogo.domain.castmember.CastMember;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberType;
import com.smarcosm.admin_catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.smarcosm.admin_catalogo.Fixture.CastMember.type;
import static com.smarcosm.admin_catalogo.Fixture.name;

@MySQLGatewayTest
class CastMemberMySQLGatewayTest {
    @Autowired
    private CastMemberMySQLGateway castMemberGateway;
    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void testDependencies() {
        Assertions.assertNotNull(castMemberGateway);
        Assertions.assertNotNull(castMemberRepository);
    }

    @Test
    public void givenAValidCastMember_whenCallsCreate_shouldPersistIt() {
        // given
        final var expectedName = name();
        final var expectedType = type();
        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId();
        Assertions.assertEquals(0, castMemberRepository.count());

        // when
        final var actualMember = castMemberGateway.create(CastMember.with(aMember));

        // then
        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualMember.getUpdatedAt());

        final var persistedMember = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), persistedMember.getId());
        Assertions.assertEquals(expectedName, persistedMember.getName());
        Assertions.assertEquals(expectedType, persistedMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), persistedMember.getCreatedAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), persistedMember.getUpdatedAt());
    }

    @Test
    public void givenAValidCastMember_whenCallsUpdate_shouldRefreshIt() {
        // given
        final var expectedName = name();
        final var expectedType = CastMemberType.ACTOR;

        final var aMember = CastMember.newMember("vind", CastMemberType.DIRECTOR);
        final var expectedId = aMember.getId();

        final var currentMember = castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());
        Assertions.assertEquals("vind", currentMember.getName());
        Assertions.assertEquals(CastMemberType.DIRECTOR, currentMember.getType());

        // when
        final var actualMember = castMemberGateway.update(CastMember.with(aMember).update(expectedName, expectedType));

        // then
        Assertions.assertEquals(1, castMemberRepository.count());

        Assertions.assertEquals(expectedId, actualMember.getId());
        Assertions.assertEquals(expectedName, actualMember.getName());
        Assertions.assertEquals(expectedType, actualMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualMember.getCreatedAt());
        Assertions.assertTrue(aMember.getUpdatedAt().isBefore(actualMember.getUpdatedAt()));

        final var persistedMember = castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedId.getValue(), persistedMember.getId());
        Assertions.assertEquals(expectedName, persistedMember.getName());
        Assertions.assertEquals(expectedType, persistedMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), persistedMember.getCreatedAt());
        Assertions.assertTrue(aMember.getUpdatedAt().isBefore(persistedMember.getUpdatedAt()));
    }
}