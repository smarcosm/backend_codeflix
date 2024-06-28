package com.smarcosm.admin_catalogo.application.video.create;

import com.smarcosm.admin_catalogo.domain.video.Video;

public record CreateVideoOutput (String id){
    public static CreateVideoOutput from(final Video aVideo){
        return new CreateVideoOutput(aVideo.getId().getValue());
    }
}
