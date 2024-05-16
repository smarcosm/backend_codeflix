package com.smarcosm.admin_catalogo.infrastructure.genre.persistence;

import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import jakarta.persistence.*;

import java.util.Objects;
// Vlad Mihalcea
@Entity
@Table(name = "genres_categories")
public class GenreCategoryJpaEntity {
    @EmbeddedId
    private GenreCategoryID id;
    @ManyToOne
    @MapsId("genreId")
    private GenreJpaEntity genre;

    public GenreCategoryJpaEntity(){}

    private GenreCategoryJpaEntity(final GenreJpaEntity aGenre, final CategoryID aCategoryID){
        this.id = GenreCategoryID.from(aGenre.getId(), aCategoryID.getValue());
        this.genre = aGenre;
    }
    public static GenreCategoryJpaEntity from(final GenreJpaEntity aGenre, final CategoryID aCategoryID){
        return new GenreCategoryJpaEntity(aGenre, aCategoryID );
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final GenreCategoryJpaEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public GenreCategoryID getId() {
        return id;
    }

    public GenreJpaEntity getGenre() {
        return genre;
    }
}
