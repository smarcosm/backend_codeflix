package com.smarcosm.admin_catalogo.application;

import com.smarcosm.admin_catalogo.domain.category.Category;

public class UseCase {
    public Category execute(){
        return new Category();
    }
}