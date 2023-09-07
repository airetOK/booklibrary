package com.booklibrary;

import com.booklibrary.conf.ApplicationConfiguration;
import com.booklibrary.dao.BookDao;
import com.booklibrary.resource.BookResource;
import com.booklibrary.resource.JwtAuthFilterLocal;
import com.booklibrary.resource.KeycloakAuthenticator;
import com.booklibrary.resource.UserLocal;
import com.booklibrary.service.BookService;
import com.booklibrary.service.impl.BookServiceImpl;
import com.booklibrary.dao.InMemoryBookLibrary;

import de.ahus1.keycloak.dropwizard.KeycloakBundle;
import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BookLibraryApplication extends Application<ApplicationConfiguration>
{
    public static void main(String[] args) throws Exception {
        new BookLibraryApplication().run(args);
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment)
    {
        final InMemoryBookLibrary bookLibrary = new InMemoryBookLibrary();
        final BookDao bookDao = new BookDao(bookLibrary);
        // book service would be injected to book resource in the future
        final BookService bookService = new BookServiceImpl(bookDao);
        final BookResource resource = new BookResource();
        environment.jersey().register(resource);
        environment.jersey().register(new AuthDynamicFeature(
                new JwtAuthFilterLocal(new KeycloakAuthenticator(configuration))));

    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
        /*bootstrap.addBundle(new KeycloakBundle<ApplicationConfiguration>() {
            @Override
            protected KeycloakConfiguration getKeycloakConfiguration(ApplicationConfiguration configuration) {
                return configuration.getKeycloakConfiguration();
            }
        });*/
    }

}