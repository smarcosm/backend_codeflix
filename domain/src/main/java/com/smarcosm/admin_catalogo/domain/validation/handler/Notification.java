package com.smarcosm.admin_catalogo.domain.validation.handler;

import com.smarcosm.admin_catalogo.domain.exception.DomainException;
import com.smarcosm.admin_catalogo.domain.validation.Error;
import com.smarcosm.admin_catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private  final List<Error> errors;

    private Notification(List<Error> errors) {
        this.errors = errors;
    }
    public static Notification create(){
        return new Notification(new ArrayList<>());
    }
    public static Notification create(final Error anError){
        return new Notification(new ArrayList<>()).append(anError);
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }
    @Override
    public Notification append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(final Validation aValidation) {

        try {
            aValidation.validate();
        }catch (final DomainException ex){
            this.errors.addAll(ex.getErrors());
        }catch (final Throwable t){
            this.errors.add(new Error((t.getMessage())));
        }
        return this;
    }

    @Override
    public boolean hasError() {
        return ValidationHandler.super.hasError();
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
