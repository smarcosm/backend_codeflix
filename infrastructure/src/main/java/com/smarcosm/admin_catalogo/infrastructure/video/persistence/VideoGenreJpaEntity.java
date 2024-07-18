package com.smarcosm.admin_catalogo.infrastructure.video.persistence;

import com.smarcosm.admin_catalogo.domain.genre.GenreID;
import jakarta.persistence.*;

@Table(name = "videos_genres")
@Entity(name = "VideoGenre")
public class VideoGenreJpaEntity {
    @EmbeddedId
    private VideoGenreID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    private VideoJpaEntity video;

    public VideoGenreJpaEntity() {
    }

    private VideoGenreJpaEntity(final VideoGenreID id, final VideoJpaEntity video) {
        this.id = id;
        this.video = video;
    }
    public static VideoGenreJpaEntity from(final VideoJpaEntity video, final GenreID genres) {
        return new VideoGenreJpaEntity(
                VideoGenreID.from(video.getId(), genres.getValue()),
                video
        );
    }
    public VideoGenreID getId() {
        return id;
    }

    public void setId(VideoGenreID id) {
        this.id = id;
    }

    public VideoJpaEntity getVideo() {
        return video;
    }

    public void setVideo(VideoJpaEntity video) {
        this.video = video;
    }
}
