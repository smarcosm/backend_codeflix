package com.smarcosm.admin_catalogo.application.video.delete;

import com.smarcosm.admin_catalogo.domain.video.VideoGateway;
import com.smarcosm.admin_catalogo.domain.video.VideoID;

import java.util.Objects;

public class DefaultDeleteVideoUseCase extends DeleteVideoUseCase{
    private final VideoGateway videoGateway;

    public DefaultDeleteVideoUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public void execute(final String anIn) {
        this.videoGateway.deleteById(VideoID.from(anIn));
    }
}
