package com.smarcosm.admin_catalogo.domain.castmember;

import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CastMemberGateway {
    CastMember create(CastMember aCastMember);
    void deleteById(CastMemberID anId);
    Optional<CastMember> findById(CastMemberID anId);
    CastMember update(CastMember aCastMember);
    Pagination<CastMember> findAll(SearchQuery aQuery);

}
