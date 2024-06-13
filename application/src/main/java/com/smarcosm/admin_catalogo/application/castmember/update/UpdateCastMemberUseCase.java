package com.smarcosm.admin_catalogo.application.castmember.update;

import com.smarcosm.admin_catalogo.application.UseCase;

public sealed abstract class UpdateCastMemberUseCase
        extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput>
        permits DefaultUpdateCastMemberUseCase {
}
