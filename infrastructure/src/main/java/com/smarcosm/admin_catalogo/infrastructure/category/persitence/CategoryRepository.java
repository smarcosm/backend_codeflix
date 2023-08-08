package com.smarcosm.admin_catalogo.infrastructure.category.persitence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryJpaEntity, String> {
}
