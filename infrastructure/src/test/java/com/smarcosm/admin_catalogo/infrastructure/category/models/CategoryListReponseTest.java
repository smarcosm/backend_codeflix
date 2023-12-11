package com.smarcosm.admin_catalogo.infrastructure.category.models;

import com.smarcosm.admin_catalogo.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTest
public class CategoryListReponseTest {
    @Autowired
    private JacksonTester<CategoryListReponse> json;


    @Test
    public void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedName = "Films";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

        final var response = new CategoryListReponse(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive,
                expectedCreatedAt,
                expectedDeletedAt
        );
        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.description", expectedDescription)
                .hasJsonPathValue("$.is_active", expectedIsActive)
                .hasJsonPathValue("$.created_at", expectedCreatedAt.toString())
                .hasJsonPathValue("$.deleted_at", expectedDeletedAt.toString());
    }


}
