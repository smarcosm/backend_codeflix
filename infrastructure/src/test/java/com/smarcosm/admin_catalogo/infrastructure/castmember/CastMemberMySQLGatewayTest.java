package com.smarcosm.admin_catalogo.infrastructure.castmember;

import com.smarcosm.admin_catalogo.MySQLGatewayTest;
import com.smarcosm.admin_catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
@MySQLGatewayTest
class CastMemberMySQLGatewayTest {
    @Autowired
    private CastMemberMySQLGateway castMemberGateway;
    @Autowired
    private CastMemberRepository castMemberRepository;

    @Test
    public void testDependencies(){
        Assertions.assertNotNull(castMemberGateway);
        Assertions.assertNotNull(castMemberRepository);
    }

}