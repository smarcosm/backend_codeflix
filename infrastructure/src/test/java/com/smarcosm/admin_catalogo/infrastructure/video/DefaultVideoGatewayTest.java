package com.smarcosm.admin_catalogo.infrastructure.video;

import com.smarcosm.admin_catalogo.IntegrationTest;
import com.smarcosm.admin_catalogo.domain.Fixture;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberGateway;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberID;
import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.domain.genre.GenreGateway;
import com.smarcosm.admin_catalogo.domain.genre.GenreID;
import com.smarcosm.admin_catalogo.domain.video.AudioVideoMedia;
import com.smarcosm.admin_catalogo.domain.video.ImageMedia;
import com.smarcosm.admin_catalogo.domain.video.Video;
import com.smarcosm.admin_catalogo.infrastructure.video.persistence.VideoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Set;

@IntegrationTest
public class DefaultVideoGatewayTest {
    @Autowired
    private DefaultVideoGateway videoGateway;
    @Autowired
    private CastMemberGateway castMemberGateway;
    @Autowired
    private CategoryGateway categoryGateway;
    @Autowired
    private GenreGateway genreGateway;
    @Autowired
    private VideoRepository videoRepository;
    @Test
    public void testInjection() {
        Assertions.assertNotNull(videoGateway);
        Assertions.assertNotNull(castMemberGateway);
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(genreGateway);
        Assertions.assertNotNull(videoRepository);
    }
    @Test
    @Transactional
    public void givenAValidVideo_whenCallsCreate_shouldPersistIt() {
        // given

        final var wesley = castMemberGateway.create(Fixture.CastMembers.wesley());
        final var aulas = categoryGateway.create(Fixture.Categories.aulas());
        final var tech = genreGateway.create(Fixture.Genres.tech());
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(aulas.getId());
        final var expectedGenres = Set.of(tech.getId());
        final var expectedMembers = Set.of(wesley.getId());
        final AudioVideoMedia expectedVideo =
                AudioVideoMedia.with("123", "video", "/media/video");
        final AudioVideoMedia expectedTrailer =
                AudioVideoMedia.with("123", "trailer", "/media/trailer");
        final ImageMedia expectedBanner =
                ImageMedia.with("123", "banner", "/media/banner");
        final ImageMedia expectedThumb =
                ImageMedia.with("123", "thumb", "/media/thumb");
        final ImageMedia expectedThumbHalf =
                ImageMedia.with("123", "thumbHalf", "/media/thumbHalf");
        final var aVideo = Video.newVideo(
                        expectedTitle,
                        expectedDescription,
                        expectedLaunchYear,
                        expectedDuration,
                        expectedOpened,
                        expectedPublished,
                        expectedRating,
                        expectedCategories,
                        expectedGenres,
                        expectedMembers
                )
                .setVideo(expectedVideo)
                .setTrailer(expectedTrailer)
                .setBanner(expectedBanner)
                .setThumbnail(expectedThumb)
                .setThumbnailHalf(expectedThumbHalf);
        // when
        System.out.println("Before persisting: " + aVideo);
        System.out.println("Actual Video: " + aVideo);
        System.out.println("Video: " + aVideo.getVideo()); // Verifica o conteúdo do Optional
        System.out.println("Trailer: " + aVideo.getTrailer());
        System.out.println("Banner: " + aVideo.getBanner());
        System.out.println("Thumbnail: " + aVideo.getThumbnail());
        System.out.println("ThumbnailHalf: " + aVideo.getThumbnailHalf());

        final var actualVideo = videoGateway.create(aVideo);

        System.out.println("After persisting: " + actualVideo);

        System.out.println("Actual Video: " + actualVideo);
        System.out.println("Video: " + actualVideo.getVideo()); // Verifica o conteúdo do Optional
        System.out.println("Trailer: " + actualVideo.getTrailer());
        System.out.println("Banner: " + actualVideo.getBanner());
        System.out.println("Thumbnail: " + actualVideo.getThumbnail());
        System.out.println("ThumbnailHalf: " + actualVideo.getThumbnailHalf());

        // then
        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());
        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchYear, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertTrue(actualVideo.getVideo().isPresent());
        Assertions.assertEquals(expectedVideo.name(), actualVideo.getVideo().get().name());

        Assertions.assertTrue(actualVideo.getTrailer().isPresent());
        Assertions.assertEquals(expectedTrailer.name(), actualVideo.getTrailer().get().name());

        Assertions.assertTrue(actualVideo.getBanner().isPresent());
        Assertions.assertEquals(expectedBanner.name(), actualVideo.getBanner().get().name());

        Assertions.assertTrue(actualVideo.getThumbnail().isPresent());
        Assertions.assertEquals(expectedThumb.name(), actualVideo.getThumbnail().get().name());

        Assertions.assertTrue(actualVideo.getThumbnailHalf().isPresent());
        Assertions.assertEquals(expectedThumbHalf.name(), actualVideo.getThumbnailHalf().get().name());



        final var persistedVideo = videoRepository.findById(actualVideo.getId().getValue()).orElseThrow(() -> new IllegalStateException("Video not found in repository"));
        Assertions.assertNotNull(persistedVideo.getVideo()); // Verifique se o vídeo persistido tem o vídeo

        Assertions.assertEquals(expectedTitle, persistedVideo.getTitle());
        Assertions.assertEquals(expectedDescription, persistedVideo.getDescription());
        Assertions.assertEquals(expectedLaunchYear, Year.of(persistedVideo.getYearLaunched()));
        Assertions.assertEquals(expectedDuration, persistedVideo.getDuration());
        Assertions.assertEquals(expectedOpened, persistedVideo.isOpened());
        Assertions.assertEquals(expectedPublished, persistedVideo.isPublished());
        Assertions.assertEquals(expectedRating, persistedVideo.getRating());
        Assertions.assertEquals(expectedCategories, persistedVideo.getCategoriesID());
        Assertions.assertEquals(expectedGenres, persistedVideo.getGenresID());
        Assertions.assertEquals(expectedMembers, persistedVideo.getCastMembersID());
        Assertions.assertEquals(expectedVideo.name(), persistedVideo.getVideo().getName());
        Assertions.assertEquals(expectedTrailer.name(), persistedVideo.getTrailer().getName());
        Assertions.assertEquals(expectedBanner.name(), persistedVideo.getBanner().getName());
        Assertions.assertEquals(expectedThumb.name(), persistedVideo.getThumbnail().getName());
        Assertions.assertEquals(expectedThumbHalf.name(), persistedVideo.getThumbnailHalf().getName());
    }
    @Test
    @Transactional
    public void givenAValidVideoWithoutRelations_whenCallsCreate_shouldPersistIt() {
        // given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.<CategoryID>of();
        final var expectedGenres = Set.<GenreID>of();
        final var expectedMembers = Set.<CastMemberID>of();
        final var aVideo = Video.newVideo(
                expectedTitle,
                expectedDescription,
                expectedLaunchYear,
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                expectedCategories,
                expectedGenres,
                expectedMembers
        );
        // when
        final var actualVideo = videoGateway.create(aVideo);
        // then
        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());
        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchYear, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertTrue(actualVideo.getVideo().isEmpty());
        Assertions.assertTrue(actualVideo.getTrailer().isEmpty());
        Assertions.assertTrue(actualVideo.getBanner().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnail().isEmpty());
        Assertions.assertTrue(actualVideo.getThumbnailHalf().isEmpty());
        final var persistedVideo = videoRepository.findById(actualVideo.getId().getValue()).get();
        Assertions.assertEquals(expectedTitle, persistedVideo.getTitle());
        Assertions.assertEquals(expectedDescription, persistedVideo.getDescription());
        Assertions.assertEquals(expectedLaunchYear, Year.of(persistedVideo.getYearLaunched()));
        Assertions.assertEquals(expectedDuration, persistedVideo.getDuration());
        Assertions.assertEquals(expectedOpened, persistedVideo.isOpened());
        Assertions.assertEquals(expectedPublished, persistedVideo.isPublished());
        Assertions.assertEquals(expectedRating, persistedVideo.getRating());
        Assertions.assertEquals(expectedCategories, persistedVideo.getCategoriesID());
        Assertions.assertEquals(expectedGenres, persistedVideo.getGenresID());
        Assertions.assertEquals(expectedMembers, persistedVideo.getCastMembersID());
        Assertions.assertNull(persistedVideo.getVideo());
        Assertions.assertNull(persistedVideo.getTrailer());
        Assertions.assertNull(persistedVideo.getBanner());
        Assertions.assertNull(persistedVideo.getThumbnail());
        Assertions.assertNull(persistedVideo.getThumbnailHalf());
    }
    @Test
    @Transactional
    public void givenAValidVideo_whenCallsUpdate_shouldPersistIt() {
        // given
        final var aVideo = videoGateway.create(Video.newVideo(
                Fixture.title(),
                Fixture.Videos.description(),
                Year.of(Fixture.year()),
                Fixture.duration(),
                Fixture.bool(),
                Fixture.bool(),
                Fixture.Videos.rating(),
                Set.of(),
                Set.of(),
                Set.of()
        ));

        final var wesley = castMemberGateway.create(Fixture.CastMembers.wesley());
        final var aulas = categoryGateway.create(Fixture.Categories.aulas());
        final var tech = genreGateway.create(Fixture.Genres.tech());

        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchYear = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(aulas.getId());
        final var expectedGenres = Set.of(tech.getId());
        final var expectedMembers = Set.of(wesley.getId());

        final AudioVideoMedia expectedVideo =
                AudioVideoMedia.with("123", "video", "/media/video");

        final AudioVideoMedia expectedTrailer =
                AudioVideoMedia.with("123", "trailer", "/media/trailer");

        final ImageMedia expectedBanner =
                ImageMedia.with("123", "banner", "/media/banner");

        final ImageMedia expectedThumb =
                ImageMedia.with("123", "thumb", "/media/thumb");

        final ImageMedia expectedThumbHalf =
                ImageMedia.with("123", "thumbHalf", "/media/thumbHalf");

        final var updatedVideo = Video.with(aVideo)
                .update(
                        expectedTitle,
                        expectedDescription,
                        expectedLaunchYear,
                        expectedDuration,
                        expectedOpened,
                        expectedPublished,
                        expectedRating,
                        expectedCategories,
                        expectedGenres,
                        expectedMembers
                )
                .setVideo(expectedVideo)
                .setTrailer(expectedTrailer)
                .setBanner(expectedBanner)
                .setThumbnail(expectedThumb)
                .setThumbnailHalf(expectedThumbHalf);

        // when
        final var actualVideo = videoGateway.update(updatedVideo);

        // then
        Assertions.assertNotNull(actualVideo);
        Assertions.assertNotNull(actualVideo.getId());

        Assertions.assertEquals(expectedTitle, actualVideo.getTitle());
        Assertions.assertEquals(expectedDescription, actualVideo.getDescription());
        Assertions.assertEquals(expectedLaunchYear, actualVideo.getLaunchedAt());
        Assertions.assertEquals(expectedDuration, actualVideo.getDuration());
        Assertions.assertEquals(expectedOpened, actualVideo.getOpened());
        Assertions.assertEquals(expectedPublished, actualVideo.getPublished());
        Assertions.assertEquals(expectedRating, actualVideo.getRating());
        Assertions.assertEquals(expectedCategories, actualVideo.getCategories());
        Assertions.assertEquals(expectedGenres, actualVideo.getGenres());
        Assertions.assertEquals(expectedMembers, actualVideo.getCastMembers());
        Assertions.assertEquals(expectedVideo.name(), actualVideo.getVideo().get().name());
        Assertions.assertEquals(expectedTrailer.name(), actualVideo.getTrailer().get().name());
        Assertions.assertEquals(expectedBanner.name(), actualVideo.getBanner().get().name());
        Assertions.assertEquals(expectedThumb.name(), actualVideo.getThumbnail().get().name());
        Assertions.assertEquals(expectedThumbHalf.name(), actualVideo.getThumbnailHalf().get().name());
        Assertions.assertNotNull(actualVideo.getCreatedAt());
        Assertions.assertTrue(actualVideo.getUpdatedAt().isAfter(aVideo.getUpdatedAt()));

        final var persistedVideo = videoRepository.findById(actualVideo.getId().getValue()).get();

        Assertions.assertEquals(expectedTitle, persistedVideo.getTitle());
        Assertions.assertEquals(expectedDescription, persistedVideo.getDescription());
        Assertions.assertEquals(expectedLaunchYear, Year.of(persistedVideo.getYearLaunched()));
        Assertions.assertEquals(expectedDuration, persistedVideo.getDuration());
        Assertions.assertEquals(expectedOpened, persistedVideo.isOpened());
        Assertions.assertEquals(expectedPublished, persistedVideo.isPublished());
        Assertions.assertEquals(expectedRating, persistedVideo.getRating());
        Assertions.assertEquals(expectedCategories, persistedVideo.getCategoriesID());
        Assertions.assertEquals(expectedGenres, persistedVideo.getGenresID());
        Assertions.assertEquals(expectedMembers, persistedVideo.getCastMembersID());
        Assertions.assertEquals(expectedVideo.name(), persistedVideo.getVideo().getName());
        Assertions.assertEquals(expectedTrailer.name(), persistedVideo.getTrailer().getName());
        Assertions.assertEquals(expectedBanner.name(), persistedVideo.getBanner().getName());
        Assertions.assertEquals(expectedThumb.name(), persistedVideo.getThumbnail().getName());
        Assertions.assertEquals(expectedThumbHalf.name(), persistedVideo.getThumbnailHalf().getName());
        Assertions.assertNotNull(persistedVideo.getCreatedAt());
        Assertions.assertTrue(persistedVideo.getUpdatedAt().isAfter(aVideo.getUpdatedAt()));
    }
}