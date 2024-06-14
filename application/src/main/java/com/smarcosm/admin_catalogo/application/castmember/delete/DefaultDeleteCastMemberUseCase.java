package com.smarcosm.admin_catalogo.application.castmember.delete;

import com.smarcosm.admin_catalogo.domain.castmember.CastMemberGateway;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberID;

import java.util.Objects;

public final class DefaultDeleteCastMemberUseCase extends DeleteCastMemberUseCase {
    private final CastMemberGateway castMemberGateway;

    public DefaultDeleteCastMemberUseCase(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public void execute(final String anId) {
        this.castMemberGateway.deleteById(CastMemberID.from(anId));
    }
}
