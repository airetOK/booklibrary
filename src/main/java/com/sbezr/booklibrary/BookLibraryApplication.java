package com.sbezr.booklibrary;

import com.sbezr.booklibrary.conf.ApplicationConfiguration;
import com.sbezr.booklibrary.dao.BookDao;
import com.sbezr.booklibrary.dao.InMemoryBookLibrary;
import com.sbezr.booklibrary.resource.BookResource;
import com.sbezr.booklibrary.service.BookService;
import com.sbezr.booklibrary.service.impl.BookServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BookLibraryApplication extends Application<ApplicationConfiguration>
{
    public static void main(String[] args) throws Exception {
        new BookLibraryApplication().run("server", "config.yml");
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
    }

}