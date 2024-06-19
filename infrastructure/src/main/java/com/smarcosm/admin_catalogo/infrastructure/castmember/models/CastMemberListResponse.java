package com.smarcosm.admin_catalogo.infrastructure.castmember.models;

public record CastMemberListResponse(
        String id,
        String name,
        String type,
        String createdAt
) {
}
