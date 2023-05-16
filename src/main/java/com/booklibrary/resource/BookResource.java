package com.booklibrary.resource;

import com.booklibrary.entity.Book;
import com.codahale.metrics.annotation.Timed;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import de.ahus1.keycloak.dropwizard.User;
import io.dropwizard.auth.Auth;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.ClientAuthorizationContext;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    @Context
    private HttpServletRequest request;

    @GET
    @Timed
    public List<Book> getAll(@Auth User user, @Context ContainerRequestContext requestContext) {
        HttpServletRequest request =
                (HttpServletRequest) requestContext.getProperty(HttpServletRequest.class.getName());
        KeycloakSecurityContext securityContext =
                (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        ClientAuthorizationContext cast = ClientAuthorizationContext.class.cast(securityContext.getAuthorizationContext());
        AuthzClient client = cast.getClient();

        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.setAudience();
        client.authorization().authorize()
        return Collections.singletonList(new Book("J.K.Rowling", "Harry Potter"));
    }
}
