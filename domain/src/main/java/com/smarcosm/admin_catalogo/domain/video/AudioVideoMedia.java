package com.smarcosm.admin_catalogo.domain.video;

import com.smarcosm.admin_catalogo.domain.ValueObject;

import java.util.Objects;

public class AudioVideoMedia extends ValueObject {
    private final String checksum;
    private final String name;
    private final String rawLocation;
    private final String encodedLocation;
    private final MediaStatus status;

    public AudioVideoMedia(
            final String checksum,
            final String name,
            final String rawLocation,
            final String encodedLocation,
            final MediaStatus status
    ) {
        this.checksum = Objects.requireNonNull(checksum);
        this.name = Objects.requireNonNull(name);
        this.rawLocation = Objects.requireNonNull(rawLocation);
        this.encodedLocation = Objects.requireNonNull(encodedLocation);
        this.status = Objects.requireNonNull(status);
    }
    public static AudioVideoMedia with(
            final String checksum,
            final String name,
            final String rawLocation,
            final String encodedLocation,
            final MediaStatus status
    ){
      return new AudioVideoMedia(checksum, name, rawLocation, encodedLocation, status);
    }

    public String Checksum() {
        return checksum;
    }

    public String name() {
        return name;
    }

    public String RawLocation() {
        return rawLocation;
    }

    public String EncodedLocation() {
        return encodedLocation;
    }

    public MediaStatus Status() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AudioVideoMedia that)) return false;
        return Objects.equals(checksum, that.checksum) && Objects.equals(rawLocation, that.rawLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checksum, rawLocation);
    }
}
