package com.smarcosm.admin_catalogo.infrastructure.configuration.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class GoogleCloudProperties implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(GoogleCloudProperties.class);
    private String credentials;
    private String projectIs;

    public String getCredentials() {
        return credentials;
    }

    public GoogleCloudProperties setCredentials(String credentials) {
        this.credentials = credentials;
        return this;
    }

    public String getProjectIs() {
        return projectIs;
    }

    public GoogleCloudProperties setProjectIs(String projectIs) {
        this.projectIs = projectIs;
        return this;
    }

    @Override
    public void afterPropertiesSet(){
        log.debug(toString());
    }

    @Override
    public String toString() {
        return "GoogleCloudProperties{" +
                ", projectIs='" + projectIs + '\'' +
                '}';
    }
}
