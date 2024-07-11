package com.smarcosm.admin_catalogo.application.video.update;

import com.smarcosm.admin_catalogo.domain.video.Video;

public record UpdateVideoOutput(
        String id
) {
    public static UpdateVideoOutput from(final Video aVideo){
        return new UpdateVideoOutput(aVideo.getId().getValue());

    }

}
