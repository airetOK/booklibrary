package com.booklibrary.resource;

import com.booklibrary.conf.ApplicationConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ahus1.keycloak.dropwizard.User;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;
import java.util.Scanner;

import static com.booklibrary.resource.KeycloakClient.getPermissions;

public class KeycloakAuthenticator  implements Authenticator<String, UserLocal> {

    private static final String KEYCLOAK_JWKS_URL = "http://hello:8080/realms/bshevRealm/protocol/openid-connect/certs";

    public KeycloakAuthenticator(ApplicationConfiguration configuration) {
    }

    @Override
    public Optional<UserLocal> authenticate(String token) throws AuthenticationException {
        try {

            PublicKey publicKey = getPublicKeyFromJWKS(KEYCLOAK_JWKS_URL);

            Claims claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.getSubject();

            getPermissions(null, token);
            // Use Keycloak Admin Client to retrieve user permissions based on the userId

            // Create and return the authenticated User object
            UserLocal user = new UserLocal();
            user.setId(userId);
            // Set user permissions

            return Optional.of(user);
        } catch (Exception e) {
            throw new AuthenticationException("Failed to authenticate token", e);
        }
    }

    public static PublicKey getPublicKeyFromJWKS(String jwksUrl) throws IOException, CertificateException {
        // Fetch JWKS from the provided URL
        String jwksJson = fetchJWKSJson(jwksUrl);

        String x5c = parseX5C(jwksJson);

        // Convert x5c to PublicKey
        byte[] decodedKey = Base64.getDecoder().decode(x5c);
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(decodedKey));
        return x509Certificate.getPublicKey();
    }

    private static String fetchJWKSJson(String jwksUrl) throws IOException {
        final HttpGet request = new HttpGet(jwksUrl);
        CloseableHttpResponse response;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()){

            response = client
                     .execute(request);
        }

        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    private static String parseX5C(String jwksJson) {
        // Parse the JWKS JSON and extract the 'x5c' field value
        // Implement your logic here to extract the 'x5c' value from the JWKS JSON
        // For example, you can use a JSON parser or manually extract the value using String manipulations
        // For simplicity, we assume the 'x5c' field is represented by "x5c_value" in this example
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jwksJson);

            // Extract the 'x5c' value from the first 'keys' entry
            JsonNode keysNode = jsonNode.get("keys");
            if (keysNode != null && keysNode.isArray() && keysNode.size() > 0) {
                JsonNode firstKeyNode = keysNode.get(0);
                JsonNode x5cNode = firstKeyNode.get("x5c");
                if (x5cNode != null && x5cNode.isArray() && x5cNode.size() > 0) {
                    return x5cNode.get(0).asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
