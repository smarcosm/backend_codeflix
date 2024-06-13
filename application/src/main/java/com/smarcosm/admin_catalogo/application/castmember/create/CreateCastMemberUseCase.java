package com.smarcosm.admin_catalogo.application.castmember.create;

import com.smarcosm.admin_catalogo.application.UseCase;

public sealed abstract class CreateCastMemberUseCase
        extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>
        permits DefaultCreateCastMemberUseCase {
}
