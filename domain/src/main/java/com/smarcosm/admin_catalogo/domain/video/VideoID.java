package com.smarcosm.admin_catalogo.domain.video;

import com.smarcosm.admin_catalogo.domain.Identifier;
import com.smarcosm.admin_catalogo.domain.utils.IdUtils;

import java.util.Objects;

public class VideoID extends Identifier {
    private final String value;

    private VideoID(final String value){
        Objects.requireNonNull(value);
        this.value = value;
    }
    public static VideoID unique(){
        return VideoID.from(IdUtils.uuid());
    }
    public static VideoID from(final String anId) {
        return new VideoID(anId);
    }
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoID that = (VideoID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
