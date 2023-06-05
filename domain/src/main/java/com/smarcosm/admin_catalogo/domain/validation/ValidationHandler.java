package com.smarcosm.admin_catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error anError);
    ValidationHandler append(ValidationHandler anHandler);
    ValidationHandler validate(Validation anValidation);

    default boolean hasError(){

        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError(){

        if (  getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().get(0);
        }else {
            return null;
        }
    }
    List<Error> getErrors();
    interface Validation{
        void validate();
    }
}
