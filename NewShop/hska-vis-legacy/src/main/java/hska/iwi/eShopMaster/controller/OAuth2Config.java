package hska.iwi.eShopMaster.controller;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
@Configuration
public class OAuth2Config {
	
	@Value("${oauth.resource:http://localhost:8001}")
    private String baseUrl;
    @Value("${oauth.authorize:http://localhost:8001/oauth/authorize}")
    private String authorizeUrl;
    @Value("${oauth.token:http://localhost:8001/oauth/token}")
    private String tokenUrl;
    
    private static OAuth2RestTemplate template = null;

    protected static OAuth2ProtectedResourceDetails resource(String username, String password) {
        ResourceOwnerPasswordResourceDetails resource;
        resource = new  ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri("http://zuul:8081/oauth/token");
        resource.setClientId("webshop");
        resource.setClientSecret("secret");
        resource.setScope(Arrays.asList("write", "read"));
		resource.setGrantType("password");
		resource.setUsername(username);
	    resource.setPassword(password);
        return resource;
    }
    
    public static OAuth2RestTemplate getTemplate() {
    	return template;
    }

    public static OAuth2RestTemplate resetTemplate(String username, String password) {
    	return template = oAuth2RestTemplate(username, password);
    }

    private static OAuth2RestTemplate oAuth2RestTemplate(String username, String password) {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        OAuth2RestTemplate client = new OAuth2RestTemplate(resource(username, password), new DefaultOAuth2ClientContext(atr));
        return client;
    }
}
