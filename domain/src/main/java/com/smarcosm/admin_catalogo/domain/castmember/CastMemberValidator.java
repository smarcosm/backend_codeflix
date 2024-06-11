package com.smarcosm.admin_catalogo.domain.castmember;

import com.smarcosm.admin_catalogo.domain.validation.ValidationHandler;
import com.smarcosm.admin_catalogo.domain.validation.Validator;

public class CastMemberValidator extends Validator {
    private final CastMember castMember;
    protected CastMemberValidator(final CastMember aMember, final ValidationHandler aHandler) {
        super(aHandler);
        this.castMember = aMember;
    }
    @Override
    public void validate() {

    }
}
