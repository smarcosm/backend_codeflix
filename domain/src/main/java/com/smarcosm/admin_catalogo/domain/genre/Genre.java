package com.smarcosm.admin_catalogo.domain.genre;

import com.smarcosm.admin_catalogo.domain.AggregateRoot;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Genre extends AggregateRoot<GenreID>{
    private String name;
    private boolean active;
    private List<CategoryID> categories;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    protected Genre(
            final GenreID anID,
            final String aName,
            final boolean isActive,
            final List<CategoryID> categories,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        super(anID);
        this.name = aName;
        this.categories =categories;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        this.deletedAt = aDeletedAt;
    }
    public static Genre newGenre(final String aName, final boolean isActive){
        final var anId = GenreID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Genre(anId, aName, isActive, new ArrayList<>(), now, now, deletedAt);
    }
    @Override
    public void validate(final ValidationHandler handler){

    }
    public static Genre with(
            final GenreID anID,
            final String aName,
            final boolean isActive,
            final List<CategoryID> categories,
            final Instant aCreatedAt,
            final Instant aUpdatedAt,
            final Instant aDeletedAt
    ) {
        return new Genre(anID, aName, isActive, categories, aCreatedAt, aUpdatedAt, aDeletedAt);
    }
    public static Genre with(final Genre aGenre) {
        return new Genre(
                aGenre.id,
                aGenre.name,
                aGenre.active,
                new ArrayList<>(aGenre.categories),
                aGenre.createdAt,
                aGenre.updatedAt,
                aGenre.deletedAt
        );
    }
    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
