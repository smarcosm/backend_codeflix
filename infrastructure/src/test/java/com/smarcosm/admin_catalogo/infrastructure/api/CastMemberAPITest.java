package com.smarcosm.admin_catalogo.infrastructure.api;

import com.smarcosm.admin_catalogo.ControllerTest;
import com.smarcosm.admin_catalogo.application.castmember.create.CreateCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.list.ListCastMembersUseCase;
import com.smarcosm.admin_catalogo.application.castmember.update.UpdateCastMemberUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ControllerTest(controllers = CastMemberAPI.class)
public class CastMemberAPITest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CreateCastMemberUseCase createCastMemberUseCase;
    @MockBean
    private DeleteCastMemberUseCase deleteCastMemberUseCase;
    @MockBean
    private GetCastMemberByIdUseCase getCastMemberByIdUseCase;
    @MockBean
    private ListCastMembersUseCase listCastMembersUseCase;
    @MockBean
    private UpdateCastMemberUseCase updateCastMemberUseCase;

}
