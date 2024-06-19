package com.smarcosm.admin_catalogo.infrastructure.castmember.models;

import com.smarcosm.admin_catalogo.domain.castmember.CastMemberType;

public record UpdateCastMemberRequest(String name, CastMemberType type) {
}
