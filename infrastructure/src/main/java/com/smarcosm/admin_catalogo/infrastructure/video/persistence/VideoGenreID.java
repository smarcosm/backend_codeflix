package com.smarcosm.admin_catalogo.infrastructure.video.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
@Embeddable
public class VideoGenreID implements Serializable {
    @Column(name = "video_id", nullable = false)
    private UUID videoId;
    @Column(name = "genre_id", nullable = false)
    private UUID genreId;

    public VideoGenreID() {
    }

    private VideoGenreID(final UUID videoId, final UUID genreId) {
        this.videoId = videoId;
        this.genreId = genreId;
    }
    public static VideoGenreID from(final UUID videoId, final UUID genreId){
        return new VideoGenreID(videoId, genreId);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoGenreID that)) return false;
        return Objects.equals(getVideoId(), that.getVideoId()) && Objects.equals(getGenreId(), that.getGenreId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVideoId(), getGenreId());
    }

    public UUID getVideoId() {
        return videoId;
    }

    public void setVideoId(UUID videoId) {
        this.videoId = videoId;
    }

    public UUID getGenreId() {
        return genreId;
    }

    public void setGenreId(UUID genreId) {
        this.genreId = genreId;
    }
}
