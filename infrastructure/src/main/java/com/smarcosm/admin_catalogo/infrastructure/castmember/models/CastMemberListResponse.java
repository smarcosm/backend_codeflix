package com.smarcosm.admin_catalogo.infrastructure.castmember.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CastMemberListResponse(
        @JsonProperty("id")String id,
        @JsonProperty("name") String name,
        @JsonProperty("type")String type,
        @JsonProperty("created_at")  String createdAt

) {
}
