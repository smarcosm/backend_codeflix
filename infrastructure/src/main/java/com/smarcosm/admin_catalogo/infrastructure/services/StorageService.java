package com.smarcosm.admin_catalogo.infrastructure.services;

import com.smarcosm.admin_catalogo.domain.video.Resource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Interface StorageService para gerenciar recursos do tipo Resource.
 */
public interface StorageService {

    /**
     * Deleta todos os recursos cujos nomes estão na coleção fornecida.
     *
     * @param names Coleção de nomes dos recursos a serem deletados.
     */
    void deleteAll(Collection<String> names);

    /**
     * Recupera um recurso pelo nome, retornando um Optional contendo o recurso se existir.
     *
     * @param name Nome do recurso.
     * @return Optional contendo o recurso ou vazio se não existir.
     */
    Optional<Resource> get(String name);

    /**
     * Lista os nomes dos recursos que começam com o prefixo fornecido.
     *
     * @param prefix Prefixo para filtrar os nomes dos recursos.
     * @return Lista de nomes dos recursos que começam com o prefixo.
     */
    List<String> list(String prefix);

    /**
     * Armazena um recurso, associando-o ao nome fornecido.
     *
     * @param name Nome do recurso.
     * @param resource Recurso a ser armazenado.
     */
    void storage(String name, Resource resource);
}
