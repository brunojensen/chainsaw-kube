package de.chainsaw.app.testsupport;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

@TestConfiguration
public class OAuthTestConfiguration {

    @Primary
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(final OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails,
                                                 final OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails, clientContext) {
            @Override
            public OAuth2AccessToken getAccessToken() throws UserRedirectRequiredException {
                return new DefaultOAuth2AccessToken("fake-token");
            }
        };
    }

    @Primary
    @Bean
    public OAuth2ClientContext clientContext() {
        return new DefaultOAuth2ClientContext() {
            @Override
            public OAuth2AccessToken getAccessToken() {
                return new DefaultOAuth2AccessToken("fake-token");
            }
        };
    }
}
