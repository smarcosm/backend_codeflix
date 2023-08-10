package com.smarcosm.admin_catalogo.infrastructure.category;

import com.smarcosm.admin_catalogo.domain.category.Category;
import com.smarcosm.admin_catalogo.domain.category.CategoryGateway;
import com.smarcosm.admin_catalogo.domain.category.CategoryID;
import com.smarcosm.admin_catalogo.domain.category.CategorySearchQuery;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;
import com.smarcosm.admin_catalogo.infrastructure.category.persitence.CategoryJpaEntity;
import com.smarcosm.admin_catalogo.infrastructure.category.persitence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {
    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category aCategory) {
        return save(aCategory);
    }
    @Override
    public void deleteById(CategoryID anId) {    }
    @Override
    public Optional<Category> findById(CategoryID anId) {
        return Optional.empty();
    }
    @Override
    public Category update(final Category aCategory) { return create(aCategory); }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        return null;
    }

    private Category save(final Category aCategory){
        return this.repository.save(CategoryJpaEntity.from(aCategory)).toAggregate();
    }
}
