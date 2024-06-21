package com.smarcosm.admin_catalogo.application.castmember.retrieve.list;

import com.smarcosm.admin_catalogo.domain.castmember.CastMember;

public record CastMemberListOutput(
        String id,
        String name,
        String type,
        String createdAt

) {
    public static CastMemberListOutput from(final CastMember aMember){
        return new CastMemberListOutput(
                aMember.getId().getValue(),
                aMember.getName(),
                aMember.getType().name(),
                aMember.getCreatedAt().toString()

        );
    }

}
