package com.smarcosm.admin_catalogo.infrastructure.genre;

import com.smarcosm.admin_catalogo.domain.genre.Genre;
import com.smarcosm.admin_catalogo.domain.genre.GenreGateway;
import com.smarcosm.admin_catalogo.domain.genre.GenreID;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.domain.pagination.SearchQuery;
import com.smarcosm.admin_catalogo.infrastructure.genre.persistence.GenreJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.genre.persistence.GenreRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class GenreMySQLGateway implements GenreGateway {
    private final GenreRepository genreRepository;

    public GenreMySQLGateway(final GenreRepository genreRepository) {
        this.genreRepository = Objects.requireNonNull(genreRepository);
    }

    @Override
    public Genre create(final Genre aGenre) {
        return save(aGenre);
    }
    @Override
    public void deleteById(final GenreID anId) {
        final var aGenreId = anId.getValue();
        if (this.genreRepository.existsById(aGenreId)){
            this.genreRepository.deleteById(aGenreId);
        }
    }

    @Override
    public Optional<Genre> findById(GenreID anId) {
        return Optional.empty();
    }

    @Override
    public Genre update(final Genre aGenre) {
        return save(aGenre);
    }

    @Override
    public Pagination<Genre> findAll(SearchQuery aQuery) {
        return null;
    }
    public Genre save(final Genre aGenre){
        // 'this.genreRepository.save()' é chamado para salvar o objeto 'aGenre' no repositório
        // 'GenreJpaEntity.from(aGenre)' converte o objeto 'aGenre' em um objeto 'GenreJpaEntity'
        // O resultado é então salvo no repositório 'genreRepository'
        return this.genreRepository.save(GenreJpaEntity.from(aGenre))
                // 'toAggregate()' é chamado no objeto salvo para converter de volta para um objeto 'Genre'
                .toAggregate();
        // O resultado é um objeto 'Genre' que foi salvo no repositório
    }
}
