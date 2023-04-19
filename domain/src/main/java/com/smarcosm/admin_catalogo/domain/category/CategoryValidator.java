package com.smarcosm.admin_catalogo.domain.category;

import com.smarcosm.admin_catalogo.domain.validation.Error;
import com.smarcosm.admin_catalogo.domain.validation.ValidationHandler;
import com.smarcosm.admin_catalogo.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private Category category;
    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }
    @Override
    public void validate(){
    if (this.category.getName() == null){
        this.validationHandler().append(new Error("'name' should not be null"));
    }
    }
}
