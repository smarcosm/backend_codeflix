package com.smarcosm.admin_catalogo.domain.video;

import com.smarcosm.admin_catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface VideoGateway {
    Video create(VideoID anId);
    void deleteById(VideoID anId);
    Optional<Video> findById(VideoID anId);
    Pagination<Video> findAll(VideoSearchQuery aQuery);
    Video update(Video aVideo);
}
