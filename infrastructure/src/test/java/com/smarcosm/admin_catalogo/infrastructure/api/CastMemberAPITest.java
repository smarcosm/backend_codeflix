package com.smarcosm.admin_catalogo.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarcosm.admin_catalogo.ControllerTest;
import com.smarcosm.admin_catalogo.Fixture;
import com.smarcosm.admin_catalogo.application.castmember.create.CreateCastMemberOutput;
import com.smarcosm.admin_catalogo.application.castmember.create.DefaultCreateCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.get.CastMemberOutput;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.list.DefaultListCastMembersUseCase;
import com.smarcosm.admin_catalogo.application.castmember.update.DefaultUpdateCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.update.UpdateCastMemberOutput;
import com.smarcosm.admin_catalogo.domain.castmember.CastMember;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberID;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberType;
import com.smarcosm.admin_catalogo.domain.exception.NotFoundException;
import com.smarcosm.admin_catalogo.domain.exception.NotificationException;
import com.smarcosm.admin_catalogo.domain.validation.Error;
import com.smarcosm.admin_catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import com.smarcosm.admin_catalogo.infrastructure.castmember.models.UpdateCastMemberRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = CastMemberAPI.class)
public class CastMemberAPITest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private DefaultCreateCastMemberUseCase createCastMemberUseCase;
    @MockBean
    private DefaultDeleteCastMemberUseCase deleteCastMemberUseCase;
    @MockBean
    private DefaultGetCastMemberByIdUseCase getCastMemberByIdUseCase;
    @MockBean
    private DefaultListCastMembersUseCase listCastMembersUseCase;
    @MockBean
    private DefaultUpdateCastMemberUseCase updateCastMemberUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCastMember_shouldReturnItsIdentifier() throws Exception {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();
        final var expectedId = CastMemberID.from("111kf1");

        final var aCommand =
                new CreateCastMemberRequest(expectedName, expectedType);

        when(createCastMemberUseCase.execute(any()))
                .thenReturn(CreateCastMemberOutput.from(expectedId));
        // when
        final var aRequest = post("/cast_members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());
        // then
        aResponse.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/cast_members/" + expectedId.getValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(expectedId.getValue())));

        Mockito.verify(createCastMemberUseCase).execute(argThat(actualCmd ->
                Objects.equals(expectedName, actualCmd.name())
                        && Objects.equals(expectedType, actualCmd.type())
        ));
    }

    @Test
    public void givenAnInvalidName_whenCallsCreateCastMember_thenShouldReturnNotification() throws Exception {
        // given
        final String expectedName = null;
        final var expectedType = Fixture.CastMember.type();
        final var expectedErrorMessage = "'name' should be not null";


        final var aCommand =
                new CreateCastMemberRequest(expectedName, expectedType);

        when(createCastMemberUseCase.execute(any()))
                .thenThrow(NotificationException.with(new Error(expectedErrorMessage)));
        // when
        final var aRequest = post("/cast_members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());
        // then
        aResponse.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        Mockito.verify(createCastMemberUseCase).execute(argThat(actualCmd ->
                Objects.equals(expectedName, actualCmd.name())
                        && Objects.equals(expectedType, actualCmd.type())
        ));
    }

    @Test
    public void givenAValidId_whenCallsGetById_shouldReturnIt() throws Exception {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();

        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId().getValue();

        when(getCastMemberByIdUseCase.execute(any()))
                .thenReturn(CastMemberOutput.from(aMember));

        // when
        final var aRequest = get("/cast_members/{id}", expectedId);
        final var response = this.mvc.perform(aRequest);

        // then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.name", equalTo(expectedName)))
                .andExpect(jsonPath("$.type", equalTo(expectedType.name())))
                .andExpect(jsonPath("$.created_at", equalTo(aMember.getCreatedAt().toString())))
                .andExpect(jsonPath("$.updated_at", equalTo(aMember.getUpdatedAt().toString())));
        verify(getCastMemberByIdUseCase).execute(eq(expectedId));

    }

    @Test
    public void givenAnInvalidId_whenCallsGetByIdAndCastMemberDoNotExists_shouldReturnNotFound() throws Exception {
        // given
        final var expectedErrorMessage = "CastMember with ID 123 was not found";
        final var expectedId = CastMemberID.from("123");

        when(getCastMemberByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(CastMember.class, expectedId));

        // when
        final var aRequest = get("/cast_members/{id}", expectedId.getValue())
                .accept(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(aRequest);

        // then
        response.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(getCastMemberByIdUseCase).execute(eq(expectedId.getValue()));

    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCastMember_shouldReturnItsIdentifier() throws Exception {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();
        final var aMember = CastMember.newMember(expectedName, expectedType);
        final var expectedId = aMember.getId();

        final var aCommand =
                new UpdateCastMemberRequest(expectedName, expectedType);

        when(updateCastMemberUseCase.execute(any()))
                .thenReturn(UpdateCastMemberOutput.from(expectedId));
        // when
        final var aRequest = MockMvcRequestBuilders.put("/cast_members/{id}", expectedId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());
        // then
        aResponse.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", Matchers.equalTo(expectedId.getValue())));

        Mockito.verify(updateCastMemberUseCase).execute(argThat(actualCmd ->
                Objects.equals(expectedId.getValue(), actualCmd.id())
                        && Objects.equals(expectedName, actualCmd.name())
                        && Objects.equals(expectedType, actualCmd.type())
        ));
    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateCastMember_thenShouldReturnNotification() throws Exception {
        // given
        final var aMember = CastMember.newMember("Vin di", CastMemberType.DIRECTOR);
        final var expectedId = aMember.getId();

        final String expectedName = null;
        final var expectedType = Fixture.CastMember.type();
        final var expectedErrorMessage = "'name' should be not null";


        final var aCommand =
                new UpdateCastMemberRequest(expectedName, expectedType);

        when(updateCastMemberUseCase.execute(any()))
                .thenThrow(NotificationException.with(new Error(expectedErrorMessage)));
        // when
        final var aRequest = MockMvcRequestBuilders.put("/cast_members/{id}", expectedId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());
        // then
        aResponse.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", equalTo(expectedErrorMessage)));

        Mockito.verify(updateCastMemberUseCase).execute(argThat(actualCmd ->
                Objects.equals(expectedId.getValue(), actualCmd.id())
                        && Objects.equals(expectedName, actualCmd.name())
                        && Objects.equals(expectedType, actualCmd.type())
        ));
    }

    @Test
    public void givenAnInvalidId_whenCallsUpdateCastMember_thenShouldReturnNotFound() throws Exception {
        // given
        final var expectedId = CastMemberID.from("123");
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMember.type();
        final var expectedErrorMessage = "CastMember with ID 123 was not found";


        final var aCommand =
                new UpdateCastMemberRequest(expectedName, expectedType);

        when(updateCastMemberUseCase.execute(any()))
                .thenThrow(NotFoundException.with(CastMember.class, expectedId));
        // when
        final var aRequest = MockMvcRequestBuilders.put("/cast_members/{id}", expectedId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var aResponse = this.mvc.perform(aRequest)
                .andDo(print());
        // then
        aResponse.andExpect(status().isNotFound())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        Mockito.verify(updateCastMemberUseCase).execute(argThat(actualCmd ->
                Objects.equals(expectedId.getValue(), actualCmd.id())
                        && Objects.equals(expectedName, actualCmd.name())
                        && Objects.equals(expectedType, actualCmd.type())
        ));
    }

    @Test
    public void givenAValidId_whenCallsDeleteById_shouldDeleteIt() throws Exception {
        // given
        final var expectedId = "123";

        doNothing().when(deleteCastMemberUseCase).execute(any());

        // when
        final var aRequest = delete("/cast_members/{id}", expectedId);

        final var response = this.mvc.perform(aRequest);

        // then
        response.andExpect(status().isNoContent());

        verify(deleteCastMemberUseCase).execute(eq(expectedId));

    }
}
