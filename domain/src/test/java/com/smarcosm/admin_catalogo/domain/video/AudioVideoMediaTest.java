package com.smarcosm.admin_catalogo.domain.video;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioVideoMediaTest {
    @Test
    public void givenValidParams_whenCallsNewAudioVideo_ShouldReturnInstance() {
        // given
        final var expectedChecksum = "abc";
        final var expectedName = "Banner.png";
        final var expectedRawLocation = "/images/ac";
        final var expectedEncodeLocation = "/images/ac-encoded";
        final var expectedStatus = MediaStatus.PENDING;

        // when
        final var actualVideo =
                AudioVideoMedia.with(expectedChecksum, expectedName, expectedRawLocation, expectedEncodeLocation, expectedStatus);

        // then
        Assertions.assertNotNull(actualVideo);
        Assertions.assertEquals(expectedChecksum, actualVideo.Checksum());
        Assertions.assertEquals(expectedName, actualVideo.Name());
        Assertions.assertEquals(expectedRawLocation, actualVideo.RawLocation());
        Assertions.assertEquals(expectedEncodeLocation, actualVideo.EncodedLocation());
        Assertions.assertEquals(expectedStatus, actualVideo.Status());
    }
    @Test
    public void givenTwoVideosWithSameChecksumAndLocation_whenCallsEquals_ShouldReturnTrue(){
        // given
        final var expectedChecksum = "abc";
        final var expectedRawLocation  = "/images/ac";

        final var video1 = AudioVideoMedia.with(expectedChecksum, "Random", expectedRawLocation, "", MediaStatus.PENDING);
        final var video2 = AudioVideoMedia.with(expectedChecksum, "Simple", expectedRawLocation, "", MediaStatus.PENDING);

        // thenAssertions.assertNotNull(actualImage);
        Assertions.assertEquals(video1, video2);
        Assertions.assertNotSame(video1, video2);

    }
    @Test
    public void givenInvalidParams_whenCallsWith_ShouldReturnError(){
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with(null, "Random", "/videos", "/videos", MediaStatus.PENDING));
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("abc", null, "/videos", "/videos", MediaStatus.PENDING));
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("abc", "Random", null, "/videos", MediaStatus.PENDING));
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("abc", "Random", "/videos", null, MediaStatus.PENDING));
        Assertions.assertThrows(NullPointerException.class, () -> AudioVideoMedia.with("abc", "Random", "/videos", "/videos", null));

    }

}