package com.booklibrary.resource;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class KeycloakClient {
    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    public static void getPermissions(String identityURL, String accessToken)
    {
        CloseableHttpResponse userInfoResponse = null;
        try {
            // Get user info by accessToken
            HttpPost userInfoRequest = new HttpPost("http://hello:8080/realms/bshevRealm/protocol/openid-connect/token");
            userInfoRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer ".concat(accessToken));
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("grant_type", "urn:ietf:params:oauth:grant-type:uma-ticket"));
            params.add(new BasicNameValuePair("audience", "resource-server"));
            params.add(new BasicNameValuePair("permission", "e"));
            userInfoRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            userInfoResponse = HTTP_CLIENT.execute(userInfoRequest);
            String response = EntityUtils.toString(userInfoResponse.getEntity());

            if (userInfoResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                OAuthValidationUtil.UserInfo userInfo = new Gson().fromJson(response, OAuthValidationUtil.UserInfo.class);
//                if (userInfo.isAssertedUser() && userInfo.isAppInstalled())
//                {
//                    return userInfo;
//                }
//                else
//                {
//                    String errMsg = "Unable to identify user, received accessToken is either issued for different user or not via OAuth flow";
//                    throw new WebApplicationException((Throwable) null, createWebResponse(errMsg, HttpStatus.SC_BAD_REQUEST));
//                }
            }
            String errMsg = "Received following error from Salesforce while validating accessToken: " + response;
        }
        catch (IOException e)
        {

        }
    }

}
