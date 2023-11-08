package com.smarcosm.admin_catalogo.infrastructure.category.models;

import com.smarcosm.admin_catalogo.JacksonTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

@JacksonTest
public class CategoryResponseTest {
    @Autowired
    private JacksonTester<CategoryResponse> json;
    public void t(){
        json.parse();
    }
}
