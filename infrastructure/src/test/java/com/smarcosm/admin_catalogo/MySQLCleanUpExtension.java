package com.smarcosm.admin_catalogo;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

public class MySQLCleanUpExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext context) {
        final var repositories = SpringExtension
                .getApplicationContext(context)
                .getBeansOfType(CrudRepository.class)
                .values();
        cleanUp(repositories);
//  Esse trecho dá erro - verificar possíveis soluções

//  final var appContext = SpringExtension
//                .getApplicationContext(context);
//
//
//        cleanUp(List.of(
//                appContext.getBean(GenreRepository.class),
//                appContext.getBean(CategoryRepository.class)
//        ));
//        final var em = appContext.getBean(TestEntityManager.class);
//        em.flush();
//        em.clear();
    }
    private void cleanUp(final Collection<CrudRepository> repositories){
        repositories.forEach(CrudRepository::deleteAll);
    }
}

