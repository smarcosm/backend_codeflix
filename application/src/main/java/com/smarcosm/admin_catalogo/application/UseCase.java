package com.smarcosm.admin_catalogo.application;

import com.smarcosm.admin_catalogo.domain.category.Category;

public abstract class UseCase<IN, OUT> {

    public  abstract OUT execute(IN anIn);
}