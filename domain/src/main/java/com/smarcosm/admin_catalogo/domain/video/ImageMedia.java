package com.smarcosm.admin_catalogo.domain.video;

import com.smarcosm.admin_catalogo.domain.ValueObject;

import java.util.Objects;

public class ImageMedia extends ValueObject {
    private final String checksum;
    private final String name;
    private final String location;

    public ImageMedia(final String checksum, final String name, final String location) {
        this.checksum = Objects.requireNonNull(checksum);
        this.name = Objects.requireNonNull(name);
        this.location = Objects.requireNonNull(location);
    }
    public static ImageMedia with(final String checksum, final String name, final String location) {
        return new ImageMedia(checksum, name, location);
    }

    public String getChecksum() {
        return checksum;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageMedia that)) return false;
        return Objects.equals(getChecksum(), that.getChecksum()) && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChecksum(), getLocation());
    }
}
