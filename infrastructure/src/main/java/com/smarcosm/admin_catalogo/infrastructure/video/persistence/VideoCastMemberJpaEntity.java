package com.smarcosm.admin_catalogo.infrastructure.video.persistence;

import com.smarcosm.admin_catalogo.domain.castmember.CastMemberID;
import jakarta.persistence.*;

@Entity(name = "VideoCastMember")
@Table(name = "videos_cast_members")
public class VideoCastMemberJpaEntity {
    @EmbeddedId
    private VideoCastMemberID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("videoId")
    private VideoJpaEntity video;

    public VideoCastMemberJpaEntity() {
    }

    public VideoCastMemberJpaEntity(final VideoCastMemberID id, final VideoJpaEntity video) {
        this.id = id;
        this.video = video;
    }
    public static VideoCastMemberJpaEntity from(final VideoJpaEntity video, final CastMemberID castMembers) {
        return new VideoCastMemberJpaEntity(
                VideoCastMemberID.from(video.getId(), castMembers.getValue()), video
        );
    }
    public VideoCastMemberID getId() {
        return id;
    }

    public void setId(VideoCastMemberID id) {
        this.id = id;
    }

    public VideoJpaEntity getVideo() {
        return video;
    }

    public void setVideo(VideoJpaEntity video) {
        this.video = video;
    }
}
