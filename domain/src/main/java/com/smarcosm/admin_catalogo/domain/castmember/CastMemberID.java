package com.smarcosm.admin_catalogo.domain.castmember;

import com.smarcosm.admin_catalogo.domain.Identifier;
import com.smarcosm.admin_catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class CastMemberID extends Identifier {
    private final String value;

    private CastMemberID(final String anId) {
        this.value = Objects.requireNonNull(anId);;
    }
    public static CastMemberID unique(){
        return CastMemberID.from(IdUtils.uuid());
    }
    public static CastMemberID from(final String anId){
        return new CastMemberID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CastMemberID that)) return false;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
