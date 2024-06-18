package com.smarcosm.admin_catalogo.infrastructure.api.controllers;

import com.smarcosm.admin_catalogo.infrastructure.api.CastMemberAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CastMemberController implements CastMemberAPI {
    @Override
    public ResponseEntity<?> create(Object input) {
        return null;
    }
}
