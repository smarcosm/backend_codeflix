package com.smarcosm.admin_catalogo.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarcosm.admin_catalogo.ControllerTest;
import com.smarcosm.admin_catalogo.Fixture;
import com.smarcosm.admin_catalogo.application.castmember.create.CreateCastMemberOutput;
import com.smarcosm.admin_catalogo.application.castmember.create.DefaultCreateCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.list.DefaultListCastMembersUseCase;
import com.smarcosm.admin_catalogo.application.castmember.update.DefaultUpdateCastMemberUseCase;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberID;
import com.smarcosm.admin_catalogo.domain.exception.NotificationException;
import com.smarcosm.admin_catalogo.domain.validation.Error;
import com.smarcosm.admin_catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(MockMvcResultMatchers.header().string("Location", "/cast_members/" + expectedId.getValue()))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
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
                .andExpect(MockMvcResultMatchers.header().string("Location", nullValue()))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        Mockito.verify(createCastMemberUseCase).execute(argThat(actualCmd ->
                Objects.equals(expectedName, actualCmd.name())
                        && Objects.equals(expectedType, actualCmd.type())
        ));
    }
}
