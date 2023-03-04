package com.sbezr.booklibrary.resource;

import com.codahale.metrics.annotation.Timed;
import com.sbezr.booklibrary.entity.Book;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @GET
    @Timed
    public List<Book> getAll() {
        return Collections.singletonList(new Book("J.K.Rowling", "Harry Potter"));
    }
}
