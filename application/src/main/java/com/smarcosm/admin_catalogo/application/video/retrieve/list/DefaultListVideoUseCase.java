package com.smarcosm.admin_catalogo.application.video.retrieve.list;

import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.video.VideoGateway;
import com.smarcosm.admin_catalogo.domain.video.VideoSearchQuery;

import java.util.Objects;

public class DefaultListVideoUseCase extends ListVideoUseCase{
    private final VideoGateway videoGateway;

    public DefaultListVideoUseCase(VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public Pagination<VideoListOutput> execute(VideoSearchQuery aQuery) {
        return this.videoGateway.findAll(aQuery).map(VideoListOutput::from);
    }
}
