package com.booklibrary.resource;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

public class JwtAuthFilterLocal extends AuthFilter<String, UserLocal> {

    public JwtAuthFilterLocal(Authenticator<String, UserLocal> authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length()).trim();

            try {
                Optional<UserLocal> user = authenticator.authenticate(token);
                if (user.isPresent()) {
                    requestContext.setSecurityContext(new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return user.get();
                        }

                        @Override
                        public boolean isUserInRole(String role) {
                            return authorizer.authorize(user.get(), role);
                        }

                        @Override
                        public boolean isSecure() {
                            return requestContext.getSecurityContext().isSecure();
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return SecurityContext.BASIC_AUTH;
                        }
                    });
                    return;
                }
            } catch (AuthenticationException e) {
                // Log or handle the authentication exception
            }
        }

        // If authentication fails, return an unauthorized response
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
