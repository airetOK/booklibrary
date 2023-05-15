package com.sbezr.booklibrary.resource;

import com.codahale.metrics.annotation.Timed;
import com.sbezr.booklibrary.entity.Book;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import de.ahus1.keycloak.dropwizard.User;
import io.dropwizard.auth.Auth;
import org.keycloak.KeycloakSecurityContext;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    @GET
    @Timed
    public List<Book> getAll(@Auth User user) {
        return Collections.singletonList(new Book("J.K.Rowling", "Harry Potter"));
    }
}
