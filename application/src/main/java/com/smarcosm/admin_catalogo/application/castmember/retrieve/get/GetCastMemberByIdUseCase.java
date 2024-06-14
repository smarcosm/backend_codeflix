package com.smarcosm.admin_catalogo.application.castmember.retrieve.get;

import com.smarcosm.admin_catalogo.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase extends  UseCase<String, CastMemberOutput>
permits DefaultGetCastMemberByIdUseCase{

}
