package com.smarcosm.admin_catalogo.domain.category;

import com.smarcosm.admin_catalogo.domain.AggregateRoot;
import com.smarcosm.admin_catalogo.domain.validation.ValidationHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

public class Category extends AggregateRoot<CategoryID> implements Cloneable{
    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeleteDate
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreationDate, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdateDate, "'updateAt' should not be null");
        this.deletedAt = aDeleteDate;
    }
    private static Instant roundInstant(Instant instant, int precision) {
        long seconds = instant.getEpochSecond();
        int nanos = instant.getNano();
        BigDecimal totalSeconds = BigDecimal.valueOf(seconds).add(BigDecimal.valueOf(nanos, 9));
        BigDecimal roundedSeconds = totalSeconds.setScale(precision, RoundingMode.HALF_UP);
        long roundedNanos = roundedSeconds.movePointRight(9).longValueExact();
        return Instant.ofEpochSecond(roundedNanos / 1_000_000_000, roundedNanos % 1_000_000_000);
    }
    public static Category newCategory(final String aName, final String aDescription, final boolean isActive){
        final var id = CategoryID.unique();
        final var now = roundInstant(Instant.now(), 6);
        final var deletedAt = isActive ? null : now;
        return new Category(id, aName, aDescription, isActive, now, now, deletedAt);
    }
    public static Category with(
            final CategoryID anId,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ){
        return new Category(
                anId,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Category with(final Category aCategory){
        return with(
                aCategory.getId(),
                aCategory.name,
                aCategory.description,
                aCategory.isActive(),
                aCategory.createdAt,
                aCategory.updatedAt,
                aCategory.deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
    new CategoryValidator(this, handler).validate();
    }
    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = roundInstant(Instant.now(), 6);
        return this;
    }
    public Category deactivate() {
        if (getDeletedAt() == null){
            this.deletedAt = roundInstant(Instant.now(), 6);
        }

        this.active = false;
        this.updatedAt = roundInstant(Instant.now(), 6);
        return this;
    }
    public Category update(
            final String aName,
            final String aDescription,
            final boolean isActive
    ) {
        if (isActive){
            activate();
        }else {
            deactivate();
        }
        this.name = aName;
        this.description = aDescription;
        this.updatedAt = roundInstant(Instant.now(), 6);
        return this;
    }
    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
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

    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}