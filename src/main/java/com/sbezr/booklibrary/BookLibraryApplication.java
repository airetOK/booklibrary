package com.sbezr.booklibrary;

import com.sbezr.booklibrary.conf.ApplicationConfiguration;
import com.sbezr.booklibrary.dao.AbstractDao;
import com.sbezr.booklibrary.dao.BookDao;
import com.sbezr.booklibrary.dao.InMemoryBookLibrary;
import com.sbezr.booklibrary.entity.Book;
import com.sbezr.booklibrary.resource.BookResource;
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
        final BookResource resource = new BookResource();
        final InMemoryBookLibrary bookLibrary = InMemoryBookLibrary.getInstance();
        // bookDao will be passed in bookResource
        AbstractDao<Book> bookDao = new BookDao(bookLibrary);
        environment.jersey().register(resource);
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    }

}