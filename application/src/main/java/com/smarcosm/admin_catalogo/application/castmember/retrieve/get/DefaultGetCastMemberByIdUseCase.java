package com.smarcosm.admin_catalogo.application.castmember.retrieve.get;

import com.smarcosm.admin_catalogo.domain.castmember.CastMember;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberGateway;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberID;
import com.smarcosm.admin_catalogo.domain.exception.NotFoundException;

import java.util.Objects;


public final class DefaultGetCastMemberByIdUseCase extends GetCastMemberByIdUseCase {
   private final CastMemberGateway castMemberGateway;

   public DefaultGetCastMemberByIdUseCase(final CastMemberGateway castMemberGateway){
       this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
   }

    @Override
    public CastMemberOutput execute(final String anIn) {
        final var  anCastMemberId = CastMemberID.from(anIn);

        return this.castMemberGateway.findById(CastMemberID.from(anIn))
                .map(CastMemberOutput::from)
                .orElseThrow(() -> NotFoundException.with(CastMember.class, anCastMemberId));
    }

}
