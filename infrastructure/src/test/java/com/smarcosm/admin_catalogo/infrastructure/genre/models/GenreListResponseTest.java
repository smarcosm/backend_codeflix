package com.smarcosm.admin_catalogo.infrastructure.genre.models;

import com.smarcosm.admin_catalogo.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTest
public class GenreListResponseTest {
    @Autowired
    private JacksonTester<GenreListResponse> json;


    @Test
    public void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedName = "Ação";
        final var expectedIsActive = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

        final var response = new GenreListResponse(
                expectedId,
                expectedName,
                expectedIsActive,
                expectedCreatedAt,
                expectedDeletedAt
        );
        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.is_active", expectedIsActive)
                .hasJsonPathValue("$.created_at", expectedCreatedAt.toString())
                .hasJsonPathValue("$.deleted_at", expectedDeletedAt.toString());
    }


}
