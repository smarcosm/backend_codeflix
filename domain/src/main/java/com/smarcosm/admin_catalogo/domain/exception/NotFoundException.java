package com.smarcosm.admin_catalogo.domain.exception;

import com.smarcosm.admin_catalogo.domain.AggregateRoot;
import com.smarcosm.admin_catalogo.domain.Identifier;
import com.smarcosm.admin_catalogo.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException{
    protected NotFoundException(final String aMessage, List<Error> anErrors) {
        super(aMessage, anErrors);
    }
    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
            ){
            final var anError = "%s with ID %s was not found".formatted(
                    anAggregate.getSimpleName(),
                    id.getValue()
            );
        return new NotFoundException(anError, Collections.emptyList());
    }
}
