package com.smarcosm.admin_catalogo.application.video.retrieve.list;

import com.smarcosm.admin_catalogo.application.UseCase;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.video.VideoSearchQuery;

public abstract class ListVideoUseCase extends UseCase<VideoSearchQuery, Pagination<VideoListOutput>> {
}
