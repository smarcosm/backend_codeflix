package com.smarcosm.admin_catalogo.application.video.retrieve.list;

import com.smarcosm.admin_catalogo.domain.video.Video;

import java.time.Instant;

public record VideoListOutput (
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt

){
    public static VideoListOutput from(final Video aVideo){
        return new VideoListOutput(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt()
        );
    }
}
