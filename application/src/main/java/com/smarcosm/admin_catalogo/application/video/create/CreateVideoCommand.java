package com.smarcosm.admin_catalogo.application.video.create;

import com.smarcosm.admin_catalogo.domain.video.Resource;

import java.util.Set;

public record CreateVideoCommand (

        String title,
        String description,
        int launchYear,
        double duration,
        boolean opened,
        boolean published,
        String rating,
        Set<String> categories,
        Set<String> genres,
        Set<String> members,
        Resource video,
        Resource trailer,
        Resource banner,
        Resource thumbnail,
        Resource thumbnailHalf
) {

    public static CreateVideoCommand with(

            final String title,
            final String description,
            final int launchYear,
            final double duration,
            final boolean opened,
            final boolean published,
            final String rating,
            final Set<String> categories,
            final Set<String> genres,
            final Set<String> members,
            final Resource video,
            final Resource trailer,
            final Resource banner,
            final Resource thumbnail,
            final Resource thumbnailHalf
    )
    {
        return new CreateVideoCommand(
                title,
                description,
                launchYear,
                duration,
                opened,
                published,
                rating,
                categories,
                genres,
                members,
                 video,
                 trailer,
                 banner,
                 thumbnail,
                 thumbnailHalf
        );
    }
}