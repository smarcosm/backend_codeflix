package com.smarcosm.admin_catalogo.application.castmember.retrieve.list;

import com.smarcosm.admin_catalogo.application.UseCase;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.pagination.SearchQuery;

public sealed abstract class ListCastMembersUseCase extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
permits DefaultListCastMembersUseCase{
}
