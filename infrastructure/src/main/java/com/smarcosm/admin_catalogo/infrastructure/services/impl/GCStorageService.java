package com.smarcosm.admin_catalogo.infrastructure.services.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.smarcosm.admin_catalogo.domain.video.Resource;
import com.smarcosm.admin_catalogo.infrastructure.services.StorageService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Implementação de StorageService que utiliza o Google Cloud Storage para gerenciar recursos do tipo Resource.
 */
public class GCStorageService implements StorageService {

    // Nome do bucket do Google Cloud Storage.
    private final String bucket;

    // Instância do serviço de armazenamento do Google Cloud.
    private final Storage storage;

    /**
     * Construtor que inicializa o bucket e o serviço de armazenamento.
     *
     * @param bucket Nome do bucket.
     * @param storage Instância do serviço de armazenamento.
     */
    public GCStorageService(final String bucket, final Storage storage) {
        this.bucket = bucket;
        this.storage = storage;
    }

    /**
     * Deleta todos os recursos cujos nomes estão na coleção fornecida.
     *
     * @param names Coleção de nomes dos recursos a serem deletados.
     */
    @Override
    public void deleteAll(final Collection<String> names) {
        final var blobs = names.stream()
                .map(name -> BlobId.of(this.bucket, name))
                .toList();
        this.storage.delete(blobs);
    }

    /**
     * Recupera um recurso pelo nome, retornando um Optional contendo o recurso se existir.
     *
     * @param name Nome do recurso.
     * @return Optional contendo o recurso ou vazio se não existir.
     */
    @Override
    public Optional<Resource> get(String name) {
        return Optional.ofNullable(this.storage.get(this.bucket, name))
                .map(blob -> Resource.with(blob.getContent(), blob.getContentType(), name, null));
    }

    /**
     * Lista os nomes dos recursos que começam com o prefixo fornecido.
     *
     * @param prefix Prefixo para filtrar os nomes dos recursos.
     * @return Lista de nomes dos recursos que começam com o prefixo.
     */
    @Override
    public List<String> list(final String prefix) {
        final var blobs = this.storage.list(this.bucket, Storage.BlobListOption.prefix(prefix));
        return StreamSupport.stream(blobs.iterateAll().spliterator(), false)
                .map(BlobInfo::getBlobId)
                .map(BlobId::getName)
                .toList();
    }

    /**
     * Armazena um recurso no Google Cloud Storage, associando-o ao nome fornecido.
     *
     * @param name Nome do recurso.
     * @param resource Recurso a ser armazenado.
     */
    @Override
    public void storage(final String name, final Resource resource) {
        final var blobInfo = BlobInfo.newBuilder(this.bucket, name)
                .setContentType(resource.contentType())
                .setCrc32cFromHexString("")
                .build();
        this.storage.create(blobInfo, resource.content());
    }
}
