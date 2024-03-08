package com.smarcosm.admin_catalogo.application.category.retrieve.list;

import com.smarcosm.admin_catalogo.application.UseCase;
import com.smarcosm.admin_catalogo.domain.pagination.SearchQuery;
import com.smarcosm.admin_catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>>{
}
