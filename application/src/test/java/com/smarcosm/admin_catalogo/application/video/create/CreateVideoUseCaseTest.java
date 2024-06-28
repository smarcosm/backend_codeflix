package com.smarcosm.admin_catalogo.application.video.create;

import com.smarcosm.admin_catalogo.application.Fixture;
import com.smarcosm.admin_catalogo.application.UseCaseTest;
import com.smarcosm.admin_catalogo.domain.castmember.CastMemberGateway;
import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import com.smarcosm.admin_catalogo.domain.genre.GenreGateway;
import com.smarcosm.admin_catalogo.domain.video.Resource;
import com.smarcosm.admin_catalogo.domain.video.Resource.Type;
import com.smarcosm.admin_catalogo.domain.video.VideoGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateVideoUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultCreateVideoUseCase useCase;
    @Mock
    private VideoGateway videoGateway;
    @Mock
    private CategoryGateway categoryGateway;
    @Mock
    private CastMemberGateway castMemberGateway;
    @Mock
    private GenreGateway genreGateway;
    @Override
    protected List<Object> getMocks() {
        return null;
    }

    @Test
    public void givenAValidCommand_whenCallsCreateVideo_shouldReturnVideoId(){
        // given
        final var expectedTitle = Fixture.title();
        final var expectedDescription = Fixture.Videos.description();
        final var expectedLaunchedAt = Year.of(Fixture.year());
        final var expectedDuration = Fixture.duration();
        final var expectedOpened = Fixture.bool();
        final var expectedPublished = Fixture.bool();
        final var expectedRating = Fixture.Videos.rating();
        final var expectedCategories = Set.of(Fixture.Categories.aulas().getId());
        final var expectedGenres = Set.of(Fixture.Genres.tech().getId());
        final var expectedMembers = Set.of(
                Fixture.CastMembers.sebastiao().getId(),
                Fixture.CastMembers.wesley().getId()
        );
        final Resource expectedVideo = Fixture.Videos.resource(Type.VIDEO);
        final Resource expectedTrailer = Fixture.Videos.resource(Type.TRAILER);
        final Resource expectedBanner = Fixture.Videos.resource(Type.BANNER);
        final Resource expectedThumb = Fixture.Videos.resource(Type.THUMBNAIL);
        final Resource expectedThumbHalf = Fixture.Videos.resource(Type.THUMBNAIL_HALF);

        final var aCommand = CreateVideoCommand.with(
                expectedTitle,
                expectedDescription,
                expectedLaunchedAt.getValue(),
                expectedDuration,
                expectedOpened,
                expectedPublished,
                expectedRating,
                asString(expectedCategories),
                asString(expectedGenres),
                asString(expectedMembers),
                expectedVideo,
                expectedTrailer,
                expectedBanner,
                expectedThumb,
                expectedThumbHalf
        );
        when(categoryGateway.existsByIds(any())).thenReturn(new ArrayList<>(expectedCategories));
        when(castMemberGateway.existsByIds(any())).thenReturn(new ArrayList<>(expectedMembers));
        when(genreGateway.existsByIds(any())).thenReturn(new ArrayList<>(expectedGenres));
        when(videoGateway.create(any())).thenAnswer(returnsFirstArg());

        // when
        final var actualResult = useCase.execute(aCommand);


        // then
        Assertions.assertNotNull(actualResult);
        Assertions.assertNotNull(actualResult.id());

        verify(videoGateway).create(argThat(actualVideo ->
                Objects.equals(expectedTitle, actualVideo.getTitle())
                && Objects.equals(expectedDescription, actualVideo.getDescription())
                && Objects.equals(expectedLaunchedAt, actualVideo.getLaunchedAt())
                && Objects.equals(expectedDuration, actualVideo.getDuration())
                && Objects.equals(expectedOpened, actualVideo.getOpened())
                && Objects.equals(expectedPublished, actualVideo.getPublished())
                && Objects.equals(expectedRating, actualVideo.getRating().getName())
                && Objects.equals((expectedCategories),actualVideo.getCategories())
                && Objects.equals((expectedGenres),actualVideo.getGenres())
                && Objects.equals((expectedMembers),actualVideo.getCastMembers())
//                && Objects.equals(expectedVideo.name(),actualVideo.getVideo().get().name())
//                && Objects.equals(expectedTrailer.name(),actualVideo.getTrailer().get().name())
//                && Objects.equals(expectedBanner.name(),actualVideo.getBanner().get().name())
//                && Objects.equals(expectedThumb.name(),actualVideo.getThumbnail().get().name())
//                && Objects.equals(expectedThumbHalf.name(),actualVideo.getThumbnailHalf().get().name())
        ));
    }
}
