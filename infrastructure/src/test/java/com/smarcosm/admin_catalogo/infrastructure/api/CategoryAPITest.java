package com.smarcosm.admin_catalogo.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarcosm.admin_catalogo.ControllerTest;
import com.smarcosm.admin_catalogo.application.category.create.CreateCategoryOutput;
import com.smarcosm.admin_catalogo.application.category.create.CreateCategoryUseCase;
import com.smarcosm.admin_catalogo.application.category.delete.DeleteCategoryUseCase;
import com.smarcosm.admin_catalogo.application.category.delete.DeleteCategoryUseCaseIT;
import com.smarcosm.admin_catalogo.application.category.retrieve.get.CategoryOutput;
import com.smarcosm.admin_catalogo.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.smarcosm.admin_catalogo.application.category.retrieve.list.CategoryListOutput;
import com.smarcosm.admin_catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import com.smarcosm.admin_catalogo.application.category.update.UpdateCategoryOutput;
import com.smarcosm.admin_catalogo.application.category.update.UpdateCategoryUseCase;
import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.domain.category.CategorySearchQuery;
import com.smarcosm.admin_catalogo.domain.exception.DomainException;
import com.smarcosm.admin_catalogo.domain.exception.NotFoundException;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.validation.Error;
import com.smarcosm.admin_catalogo.domain.validation.handler.Notification;
import com.smarcosm.admin_catalogo.infrastructure.category.models.CreateCategoryApiInput;
import com.smarcosm.admin_catalogo.infrastructure.category.models.UpdateCategoryApiInput;
import io.vavr.API;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.web.servlet.function.ServerResponse.status;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;
    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;
    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;
    @MockBean
    private DeleteCategoryUseCase deleteCategoryUseCase;
    @MockBean
    private ListCategoriesUseCase listCategoriesUseCase;
    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);
        when(createCategoryUseCase.execute(any()))
                .thenReturn(API.Right(CreateCategoryOutput.from("123")));
        // when
        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request)
                .andDo(print());
        // then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/categories/123"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo("123")));

        Mockito.verify(createCategoryUseCase, Mockito.times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())

        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {
        // given
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";
        final var aInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);
        when(createCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedMessage))));
        // when
        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));
        final var response = this.mvc.perform(request)
                .andDo(print());
        // then
        response.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.equalTo(expectedMessage)));

        Mockito.verify(createCategoryUseCase, Mockito.times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())

        ));
    }

    @Test
    public void givenAInvalidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
        // given
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";
        final var aInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);
        when(createCategoryUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedMessage)));
        // when
        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        final var response = this.mvc.perform(request)
                .andDo(print());
        // then
        response.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.equalTo(expectedMessage)));

        Mockito.verify(createCategoryUseCase, Mockito.times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())

        ));
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {
        //given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId().getValue();

        //when
        final var request = get("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        when(getCategoryByIdUseCase.execute(any()))
                .thenReturn(CategoryOutput.from(aCategory));

        final var response = this.mvc.perform(request)
                .andDo(print());
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(expectedId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo(expectedName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", equalTo(expectedDescription)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_active", equalTo(expectedIsActive)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_at", equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updated_at", equalTo(aCategory.getUpdatedAt().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deleted_at", equalTo(aCategory.getDeletedAt())))
        ;


        verify(getCategoryByIdUseCase, times(1)).execute(eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() throws Exception {
        // givem
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedId = CategoryID.from("123");
        when(getCategoryByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(
                        Category.class, expectedId
                ));
        // when
        final var request = get("/categories/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON);


        final var response = this.mvc.perform(request)
                .andDo(print());
        // then
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo(expectedErrorMessage)))

        ;
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() throws Exception {
        //given
        final var expectedId = "123";
        final var expectedName = "Films";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        when(updateCategoryUseCase.execute(any()))
                .thenReturn(Right(UpdateCategoryOutput.from(expectedId)));

        //when
        final var aCommand = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);
        final var request = put("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aCommand));


        final var response = this.mvc.perform(request)
                .andDo(print());
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(expectedId)));


        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())));
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() throws Exception {
        //given
        final var expectedId = "not-found";
        final var expectedName = "Films";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Category with ID not-found was not found";

        when(updateCategoryUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, CategoryID.from(expectedId)));

        //when
        final var aCommand = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);
        final var request = put("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aCommand));


        final var response = this.mvc.perform(request)
                .andDo(print());
        //then
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo(expectedErrorMessage)));


        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() throws Exception {
        //given
        final var expectedId = "123";
        final var expectedName = "Films";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        when(updateCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedErrorMessage))));

        //when
        final var aCommand = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);
        final var request = put("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aCommand));


        final var response = this.mvc.perform(request)
                .andDo(print());
        //then
        response.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(expectedErrorCount)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));


        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())));
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldReturnNocontent() throws Exception {
        //given
        final var expectedId = "123";

        doNothing()
                .when(deleteCategoryUseCase).execute(any());
        //when
        final var request = delete("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());
        //then
        response.andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(deleteCategoryUseCase, times(1)).execute(eq(expectedId));
    }
    @Test
    public void givenValidParams_whenCallsListCategories_shouldReturnCategories() throws Exception {
        //given
        final var aCategory = Category.newCategory("Movies", null, true);
        final var expectedPage =0;
        final var expectedPerPage =10;
        final var expectedTerms = "movies";
        final var expectedShort = "description";
        final var expectedDirection = "desc";
        final var expectedItemsCount = 1;
        final var expectedTotal = 1;
        final var expectedItems = List.of(CategoryListOutput.from(aCategory));


        when(listCategoriesUseCase.execute(any()))
                .thenReturn(new Pagination<>(
                        expectedPage, expectedPerPage, expectedTotal, expectedItems
                ));
        //when
        final var request = get("/categories")
                .queryParam("page", String.valueOf((expectedPage)))
                .queryParam("perPage", String.valueOf((expectedPerPage)))
                .queryParam("sort", String.valueOf((expectedShort)))
                .queryParam("dir", String.valueOf((expectedDirection)))
                .queryParam("search", String.valueOf((expectedTerms)))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());
        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.current_page", equalTo(expectedPage)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.per_page", equalTo(expectedPerPage)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", equalTo(expectedTotal)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", hasSize(expectedItemsCount)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].id", equalTo(aCategory.getId().getValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].name", equalTo(aCategory.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].description", equalTo(aCategory.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].is_active", equalTo(aCategory.isActive())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].created_at", equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].updated_at", equalTo(aCategory.getUpdatedAt().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].deleted_at", equalTo(aCategory.getDeletedAt())));
        verify(listCategoriesUseCase, times(1)).execute(argThat(query ->
                Objects.equals(expectedPage, query.page())
                && Objects.equals(expectedPerPage, query.perPage())
                && Objects.equals(expectedDirection, query.direction())
                && Objects.equals(expectedShort, query.sort())
                && Objects.equals(expectedTerms, query.terms())
        ));
    }
}

