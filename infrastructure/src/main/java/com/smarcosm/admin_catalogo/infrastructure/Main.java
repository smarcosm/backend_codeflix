package com.smarcosm.admin_catalogo.infrastructure;

import com.smarcosm.admin_catalogo.application.category.create.CreateCategoryUseCase;
import com.smarcosm.admin_catalogo.application.category.delete.DeleteCategoryUseCase;
import com.smarcosm.admin_catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.smarcosm.admin_catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import com.smarcosm.admin_catalogo.application.category.update.UpdateCategoryUseCase;
import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.infrastructure.category.persitence.CategoryJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.category.persitence.CategoryRepository;
import com.smarcosm.admin_catalogo.infrastructure.configuration.WebServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }
//    @Bean
//    @DependsOnDatabaseInitialization
//    ApplicationRunner runner(
//            @Autowired CreateCategoryUseCase createCategoryUseCase,
//            @Autowired UpdateCategoryUseCase updateCategoryUseCase,
//            @Autowired GetCategoryByIdUseCase getCategoryByIdUseCase,
//            @Autowired ListCategoriesUseCase listCategoriesUseCase,
//            @Autowired DeleteCategoryUseCase deleteCategoryUseCase
//            ) {
//        return args -> {
//
//        };
//    }

}