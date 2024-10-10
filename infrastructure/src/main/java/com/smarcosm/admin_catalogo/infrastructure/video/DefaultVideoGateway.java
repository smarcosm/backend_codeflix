package com.smarcosm.admin_catalogo.infrastructure.video;

import com.smarcosm.admin_catalogo.domain.Identifier;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.video.*;
import com.smarcosm.admin_catalogo.infrastructure.utils.SqlUtils;
import com.smarcosm.admin_catalogo.infrastructure.video.persistence.VideoJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.video.persistence.VideoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultVideoGateway implements VideoGateway {
    private final VideoRepository videoRepository;

    public DefaultVideoGateway(final VideoRepository videoRepository) {
        this.videoRepository = Objects.requireNonNull(videoRepository);
    }

    @Override
    @Transactional
    public Video create(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public void deleteById(final VideoID anId) {
        final var aVideoId = anId.getValue();
        if (this.videoRepository.existsById(aVideoId)){
            this.videoRepository.deleteById(aVideoId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Video> findById(final VideoID anId) {
        return this.videoRepository.findById(anId.getValue()).map(VideoJpaEntity::toAggregate);
    }

    @Override
    public Pagination<VideoPreview> findAll(VideoSearchQuery aQuery) {

        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var actualPage = this.videoRepository.findAll(
                SqlUtils.like(aQuery.terms()),
                toString(aQuery.castMembers()),
                toString(aQuery.categories()),
                toString(aQuery.genres()),
                page
        );

        return new Pagination<>(
                actualPage.getNumber(),
                actualPage.getSize(),
                actualPage.getTotalElements(),
                actualPage.toList()
        );

    }
    private Set<String> toString(final Set<? extends Identifier> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }

        return ids.stream()
                .map(Identifier::getValue)
                .collect(Collectors.toSet());
    }
    @Override
    @Transactional
    public Video update(Video aVideo) {
        return save(aVideo);
    }

    private Video save(Video aVideo) {
        return this.videoRepository.save(
                VideoJpaEntity.from(aVideo)).toAggregate();
    }
}
