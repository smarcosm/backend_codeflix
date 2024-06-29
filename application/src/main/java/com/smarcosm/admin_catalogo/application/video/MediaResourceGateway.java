package com.smarcosm.admin_catalogo.application.video;

import com.smarcosm.admin_catalogo.domain.video.AudioVideoMedia;
import com.smarcosm.admin_catalogo.domain.video.ImageMedia;
import com.smarcosm.admin_catalogo.domain.video.Resource;
import com.smarcosm.admin_catalogo.domain.video.VideoID;

public interface MediaResourceGateway {
    AudioVideoMedia storeVideoMedia(VideoID anId, Resource aResource);
    ImageMedia storeImage(VideoID anId, Resource aResource);
    void clearResources(VideoID anId);

}
