package com.smarcosm.admin_catalogo.domain.validation;

public class Validator {
    private final ValidationHandler handler;
    protected Validator(final ValidationHandler aHandler){
        this.handler = aHandler;
    }

    public void validate() {

    }

    protected ValidationHandler validationHandler(){
        return this.handler;
    }
}
