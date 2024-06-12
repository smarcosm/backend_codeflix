package com.smarcosm.admin_catalogo.application.castmember.create;

import com.smarcosm.admin_catalogo.domain.castmember.CastMember;

public record CreateCastMemberOutput(
        String id
) {
    public static CreateCastMemberOutput from(final CastMember aMember) {
        return new CreateCastMemberOutput(aMember.getId().getValue());

    }

}
