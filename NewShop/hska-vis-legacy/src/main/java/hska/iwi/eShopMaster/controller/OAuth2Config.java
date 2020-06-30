package hska.iwi.eShopMaster.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
@Configuration
public class OAuth2Config {
	
	@Value("${oauth.resource:http://localhost:8081}")
    private String baseUrl;
    @Value("${oauth.authorizehttp://localhost:8081/users-service/oauth/authorize}")
    private String authorizeUrl;
    @Value("${oauth.token:http://localhost:8081/users-service/oauth/token}")
    private String tokenUrl;

    @Bean
    protected OAuth2ProtectedResourceDetails resource() {
        AuthorizationCodeResourceDetails resource;
        resource = new AuthorizationCodeResourceDetails();
        resource.setUserAuthorizationUri(authorizeUrl);
        resource.setAccessTokenUri(tokenUrl);
        resource.setClientId("webshop");
        resource.setClientSecret("secret");
        resource.setScope(Arrays.asList("write", "read"));
        return resource;
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
    }
}
