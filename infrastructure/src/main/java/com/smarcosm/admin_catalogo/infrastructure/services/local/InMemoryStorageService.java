package com.smarcosm.admin_catalogo.infrastructure.services.local;

import com.smarcosm.admin_catalogo.domain.video.Resource;
import com.smarcosm.admin_catalogo.infrastructure.services.StorageService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementação de StorageService que usa armazenamento em memória para recursos do tipo Resource.
 */
public class InMemoryStorageService implements StorageService {

    // Armazena os recursos em um ConcurrentHashMap para garantir segurança em operações concorrentes.
    private final Map<String, Resource> storage;

    // Construtor que inicializa o mapa de armazenamento.
    public InMemoryStorageService() {
        this.storage = new ConcurrentHashMap<>();
    }

    // Retorna o mapa de armazenamento.
    public Map<String, Resource> storage(){
        return this.storage;
    }

    // Limpa todos os itens armazenados.
    public void reset(){
        this.storage.clear();
    }

    @Override
    public void deleteAll(final Collection<String> names) {
        names.forEach(this.storage::remove);
    }

    @Override
    public Optional<Resource> get(final String name) {
        return Optional.ofNullable(this.storage.get(name));
    }

    @Override
    public List<String> list(final String prefix) {
        if (prefix == null) {
            return Collections.emptyList();
        }
        return this.storage.keySet().stream()
                .filter(it -> it.startsWith(prefix))
                .toList();
    }

    @Override
    public void storage(String name, Resource resource) {
        this.storage.put(name, resource);
    }
}
