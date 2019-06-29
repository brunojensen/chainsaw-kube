package de.chainsaw.app.bank.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AccountForeignConfig {

  @Value("${app.foreign-service.account.url:EMPTY}")
  private String accountUrl;

  @Bean
  public RestTemplate accountRestTemplate() {
    return new RestTemplateBuilder()
        .rootUri(accountUrl)
        .setConnectTimeout(Duration.ofSeconds(15))
        .setReadTimeout(Duration.ofSeconds(15))
        .build();
  }

}
