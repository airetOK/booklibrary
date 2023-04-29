package com.sbezr.booklibrary;

import com.sbezr.booklibrary.conf.ApplicationConfiguration;
import com.sbezr.booklibrary.dao.BookDao;
import com.sbezr.booklibrary.dao.InMemoryBookLibrary;
import com.sbezr.booklibrary.resource.BookResource;
import com.sbezr.booklibrary.service.BookService;
import com.sbezr.booklibrary.service.impl.BookServiceImpl;

import de.ahus1.keycloak.dropwizard.KeycloakBundle;
import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import io.dropwizard.Application;
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

    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());

        bootstrap.addBundle(new KeycloakBundle<ApplicationConfiguration>() {
            @Override
            protected KeycloakConfiguration getKeycloakConfiguration(ApplicationConfiguration configuration) {
                return configuration.getKeycloakConfiguration();
            }
            /* OPTIONAL: override getUserClass(), createAuthorizer() and createAuthenticator() if you want to use
             * a class other than de.ahus1.keycloak.dropwizard.User to be injected by @Auth */
        });
    }

}