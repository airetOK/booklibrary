package com.booklibrary.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import io.dropwizard.Configuration;

public class ApplicationConfiguration extends Configuration {

    private de.ahus1.keycloak.dropwizard.KeycloakConfiguration keycloakConfiguration = new KeycloakConfiguration();

    public de.ahus1.keycloak.dropwizard.KeycloakConfiguration getKeycloakConfiguration() {
        return keycloakConfiguration;
    }

    @JsonProperty("keycloakConfiguration")
    public void setKeycloakConfiguration(KeycloakConfiguration keycloakConfiguration) {
        this.keycloakConfiguration = keycloakConfiguration;
    }
}