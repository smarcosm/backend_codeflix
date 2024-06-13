package com.smarcosm.admin_catalogo.application.castmember.update;

import com.smarcosm.admin_catalogo.domain.castmember.CastMember;

public record UpdateCastMemberOutput(
        String id
) {
    public static UpdateCastMemberOutput from(final CastMember aMember) {
        return new UpdateCastMemberOutput(aMember.getId().getValue());

    }

}
