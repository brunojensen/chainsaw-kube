package de.chainsaw.app.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Client
@Configuration
public class AccountForeignConfig {

  @Bean
  public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails details) {
    return new OAuth2RestTemplate(details);
  }

}
