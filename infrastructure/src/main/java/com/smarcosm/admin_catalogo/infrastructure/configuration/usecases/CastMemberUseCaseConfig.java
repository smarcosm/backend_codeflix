package com.smarcosm.admin_catalogo.infrastructure.configuration.usecases;


import com.smarcosm.admin_catalogo.application.castmember.create.CreateCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.create.DefaultCreateCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.list.DefaultListCastMembersUseCase;
import com.smarcosm.admin_catalogo.application.castmember.retrieve.list.ListCastMembersUseCase;
import com.smarcosm.admin_catalogo.application.castmember.update.DefaultUpdateCastMemberUseCase;
import com.smarcosm.admin_catalogo.application.castmember.update.UpdateCastMemberUseCase;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CastMemberUseCaseConfig {
    private final CastMemberGateway castMemberGateway;

    public CastMemberUseCaseConfig(CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase(){
        return new DefaultCreateCastMemberUseCase(castMemberGateway);
    }
    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase(){ return new DefaultUpdateCastMemberUseCase( castMemberGateway); }
    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase(){ return new DefaultGetCastMemberByIdUseCase(castMemberGateway); }
    @Bean
    public ListCastMembersUseCase listCastMemberUseCase(){ return new DefaultListCastMembersUseCase(castMemberGateway); }
    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase(){ return new DefaultDeleteCastMemberUseCase( castMemberGateway); }
}
