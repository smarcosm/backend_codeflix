package com.smarcosm.admin_catalogo.infrastructure.castmember.presenter;

import com.smarcosm.admin_catalogo.application.castmember.retrieve.get.CastMemberOutput;
import com.smarcosm.admin_catalogo.infrastructure.castmember.models.CastMemberResponse;

public interface CastMemberPresenter {
    static CastMemberResponse present(final CastMemberOutput aMember) {
        return new CastMemberResponse(
                aMember.id(),
                aMember.name(),
                aMember.type(),
                aMember.createdAt(),
                aMember.updatedAt()
        );
    }
}
