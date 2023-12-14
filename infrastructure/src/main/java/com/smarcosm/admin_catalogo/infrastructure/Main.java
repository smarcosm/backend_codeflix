package com.smarcosm.admin_catalogo.infrastructure;

import com.smarcosm.admin_catalogo.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
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