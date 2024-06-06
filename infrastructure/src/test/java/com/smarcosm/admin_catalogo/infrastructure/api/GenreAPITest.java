package com.smarcosm.admin_catalogo.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarcosm.admin_catalogo.ControllerTest;
import com.smarcosm.admin_catalogo.application.genre.create.CreateGenreOutput;
import com.smarcosm.admin_catalogo.application.genre.create.CreateGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.delete.DeleteGenreUseCase;
import com.smarcosm.admin_catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import com.smarcosm.admin_catalogo.application.genre.update.UpdateGenreUseCase;
import com.smarcosm.admin_catalogo.domain.exception.NotificationException;
import com.smarcosm.admin_catalogo.domain.validation.Error;
import com.smarcosm.admin_catalogo.domain.validation.handler.Notification;
import com.smarcosm.admin_catalogo.infrastructure.genre.models.CreateGenreRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ControllerTest(controllers = GenreAPI.class)
public class GenreAPITest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CreateGenreUseCase createGenreUseCase;
    @MockBean
    private GetGenreByIdUseCase getGenreByIdUseCase;
    @MockBean
    private UpdateGenreUseCase updateGenreUseCase;
    @MockBean
    private DeleteGenreUseCase deleteGenreUseCase;
    @Test
    public void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId() throws Exception {
        // given
        final var expectedName = "Ação";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = true;
        final var expectedId = "123";

        final var aInput =
                new CreateGenreRequest(expectedName, expectedCategories, expectedIsActive);
        when(createGenreUseCase.execute(any()))
                .thenReturn(CreateGenreOutput.from(expectedId));
        // when
        final var aRequest = post("/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());
        // then
        aResponse.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/genre" + expectedId))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo("123")));

        Mockito.verify(createGenreUseCase, Mockito.times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedCategories, cmd.categories())
                        && Objects.equals(expectedIsActive, cmd.isActive())

        ));
    }
    @Test
    public void givenAInvalidName_whenCallsCreateGenre_thenShouldReturnNotification() throws Exception {
        // given
        final String expectedName = null;
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should be not null";
        final var aInput =
                new CreateGenreRequest(expectedName, expectedCategories, expectedIsActive);
        when(createGenreUseCase.execute(any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));
        // when
        final var aRequest = post("/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());
        // then
        aResponse.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", nullValue()))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        Mockito.verify(createGenreUseCase, Mockito.times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedCategories, cmd.categories())
                        && Objects.equals(expectedIsActive, cmd.isActive())

        ));
    }

}

