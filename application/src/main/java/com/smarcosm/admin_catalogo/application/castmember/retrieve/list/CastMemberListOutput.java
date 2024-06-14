package com.smarcosm.admin_catalogo.application.castmember.retrieve.list;

import com.smarcosm.admin_catalogo.domain.castmember.CastMember;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberListOutput(
        String id,
        String name,
        CastMemberType type,
        Instant createdAt

) {
    public static CastMemberListOutput from(final CastMember aMember){
        return new CastMemberListOutput(
                aMember.getId().getValue(),
                aMember.getName(),
                aMember.getType(),
                aMember.getCreatedAt()

        );
    }

}
