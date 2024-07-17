package com.smarcosm.admin_catalogo.infrastructure.video.persistence;

import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.domain.genre.GenreID;
import com.smarcosm.admin_catalogo.domain.video.Rating;
import com.smarcosm.admin_catalogo.domain.video.Video;
import com.smarcosm.admin_catalogo.domain.video.VideoID;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.Year;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Table(name = "videos")
@Entity(name = "Videos")
public class VideoJpaEntity {
    @Id
    private UUID id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", length = 4000)
    private String description;
    @Column(name = "year_launched", nullable = false)
    private int yearLaunched;
    @Column(name = "opened", nullable = false)
    private boolean opened;
    @Column(name = "published", nullable = false)
    private boolean published;
    @Column(name = "rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;
    @Column(name = "duration", precision = 2)
    private double duration;
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Column(name = "video_id")
    private AudioVideoMediaJpaEntity video;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Column(name = "trailer_id")
    private AudioVideoMediaJpaEntity trailer;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Column(name = "banner_id")
    private ImageMediaJpaEntity banner;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Column(name = "thumbnail_id")
    private ImageMediaJpaEntity thumbnail;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Column(name = "thumbnail_half_id")
    private ImageMediaJpaEntity thumbnailHalf;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoCategoryJpaEntity> categories;
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoGenreJpaEntity> genres;
    public VideoJpaEntity() {
        this.categories = new HashSet<>(3);
        this.genres = new HashSet<>(2);
    }

    private VideoJpaEntity(
            final UUID id,
            final String title,
            final String description,
            final int yearLaunched,
            final boolean opened,
            final boolean published,
            final Rating rating,
            final double duration,
            final Instant createdAt,
            final Instant updatedAt,
            final AudioVideoMediaJpaEntity video,
            final AudioVideoMediaJpaEntity trailer,
            final ImageMediaJpaEntity banner,
            final ImageMediaJpaEntity thumbnail,
            final ImageMediaJpaEntity thumbnailHalf

    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.yearLaunched = yearLaunched;
        this.opened = opened;
        this.published = published;
        this.rating = rating;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.video = video;
        this.trailer = trailer;
        this.banner = banner;
        this.thumbnail = thumbnail;
        this.thumbnailHalf = thumbnailHalf;
        this.categories = new HashSet<>(3);
        this.genres = new HashSet<>(2);
    }


    public static VideoJpaEntity from(final Video aVideo) {
        final var entity = new VideoJpaEntity(
                UUID.fromString(aVideo.getId().getValue()),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getLaunchedAt().getValue(),
                aVideo.getOpened(),
                aVideo.getPublished(),
                aVideo.getRating(),
                aVideo.getDuration(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt(),
                aVideo.getVideo().map(AudioVideoMediaJpaEntity::from).orElse(null),
                aVideo.getTrailer().map(AudioVideoMediaJpaEntity::from).orElse(null),
                aVideo.getBanner().map(ImageMediaJpaEntity::from).orElse(null),
                aVideo.getThumbnail().map(ImageMediaJpaEntity::from).orElse(null),
                aVideo.getThumbnailHalf().map(ImageMediaJpaEntity::from).orElse(null)
        );
       aVideo.getCategories().forEach(entity::addCategory);
       aVideo.getGenres().forEach(entity::addGenre);
        return entity;
    }

    public Video toAggregate() {
        return Video.with(
                VideoID.from(getId()),
                getTitle(),
                getDescription(),
                Year.of(getYearLaunched()),
                getDuration(),
                isOpened(),
                isPublished(),
                getRating(),
                getCreatedAt(),
                getUpdatedAt(),
                Optional.ofNullable(getBanner()).map(ImageMediaJpaEntity::toDomain).orElse(null),
                Optional.ofNullable(getThumbnail()).map(ImageMediaJpaEntity::toDomain).orElse(null),
                Optional.ofNullable(getThumbnailHalf()).map(ImageMediaJpaEntity::toDomain).orElse(null),
                Optional.ofNullable(getTrailer()).map(AudioVideoMediaJpaEntity::toDomain).orElse(null),
                Optional.ofNullable(getVideo()).map(AudioVideoMediaJpaEntity::toDomain).orElse(null),
                getCategories().stream().map(it -> CategoryID.from(it.getId().getCategoryId())).collect(Collectors.toSet()),
                getGenres().stream().map(it -> GenreID.from(it.getId().getGenreId())).collect(Collectors.toSet()),
                null

        );
    }
    public void addCategory(final CategoryID anId){ this.categories.add(VideoCategoryJpaEntity.from(this, anId));}
    public void addGenre(final GenreID anId){ this.genres.add(VideoGenreJpaEntity.from(this, anId)); }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearLaunched() {
        return yearLaunched;
    }

    public void setYearLaunched(int yearLaunched) {
        this.yearLaunched = yearLaunched;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AudioVideoMediaJpaEntity getVideo() {
        return video;
    }

    public void setVideo(AudioVideoMediaJpaEntity video) {
        this.video = video;
    }

    public AudioVideoMediaJpaEntity getTrailer() {
        return trailer;
    }

    public void setTrailer(AudioVideoMediaJpaEntity trailer) {
        this.trailer = trailer;
    }

    public ImageMediaJpaEntity getBanner() {
        return banner;
    }

    public void setBanner(ImageMediaJpaEntity banner) {
        this.banner = banner;
    }

    public ImageMediaJpaEntity getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageMediaJpaEntity thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageMediaJpaEntity getThumbnailHalf() {
        return thumbnailHalf;
    }

    public void setThumbnailHalf(ImageMediaJpaEntity thumbnailHalf) {
        this.thumbnailHalf = thumbnailHalf;
    }

    public Set<VideoCategoryJpaEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<VideoCategoryJpaEntity> categories) {
        this.categories = categories;
    }

    public Set<VideoGenreJpaEntity> getGenres() {
        return genres;
    }

    public void setGenres(Set<VideoGenreJpaEntity> genres) {
        this.genres = genres;
    }
}