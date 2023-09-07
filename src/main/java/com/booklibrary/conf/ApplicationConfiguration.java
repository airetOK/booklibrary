package com.booklibrary.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import io.dropwizard.Configuration;

import java.util.Map;

public class ApplicationConfiguration extends Configuration {

    private de.ahus1.keycloak.dropwizard.KeycloakConfiguration keycloakConfiguration = new KeycloakConfiguration();

    private Map<String, String> authentication;

    public de.ahus1.keycloak.dropwizard.KeycloakConfiguration getKeycloakConfiguration() {
        return keycloakConfiguration;
    }

    public Map<String, String> getAuthentication() {
        return authentication;
    }

    @JsonProperty("authentication")
    public void setAuthentication(Map<String, String> authentication)
    {
        this.authentication = authentication;
    }
    @JsonProperty("keycloakConfiguration")
    public void setKeycloakConfiguration(KeycloakConfiguration keycloakConfiguration) {
        this.keycloakConfiguration = keycloakConfiguration;
    }
}