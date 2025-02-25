package com.smarcosm.admin_catalogo.infrastructure.configuration;

import com.smarcosm.admin_catalogo.infrastructure.configuration.properties.GoogleCloudProperties;
import com.smarcosm.admin_catalogo.infrastructure.configuration.properties.GoogleStorageProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Configuration
@Profile({"development", "production"})
public class GoogleCloudConfig {

    @Bean
    @ConfigurationProperties("google.cloud")
    public GoogleCloudProperties googleCloudProperties() {
        return new GoogleCloudProperties();
    }

    @Bean
    @ConfigurationProperties("google.cloud.storage.catalogo-video")
    public GoogleStorageProperties googleStorageProperties() {
        return new GoogleStorageProperties();
    }

    }