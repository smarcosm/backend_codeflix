package com.smarcosm.admin_catalogo.infrastructure.castmember.models;

import com.smarcosm.admin_catalogo.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberResponse(
        String id,
        String name,
        CastMemberType type,
        Instant createdAt,
        Instant updatedAt
) {


}
