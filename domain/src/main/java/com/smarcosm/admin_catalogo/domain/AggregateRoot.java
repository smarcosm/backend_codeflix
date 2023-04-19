package com.smarcosm.admin_catalogo.domain;

import com.smarcosm.admin_catalogo.domain.validation.ValidationHandler;

public class AggregateRoot<ID extends Identifier> extends Entity<ID>{

    protected AggregateRoot(final ID id){
        super(id);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }
}
