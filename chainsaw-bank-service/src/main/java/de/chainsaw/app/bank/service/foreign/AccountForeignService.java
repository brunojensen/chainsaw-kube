package de.chainsaw.app.bank.service.foreign;

import de.chainsaw.app.bank.exception.InternalServerException;
import de.chainsaw.app.bank.dto.AccountDto;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountForeignService {

  @Value("${app.foreign-service.account.url:EMPTY}")
  private String accountUrl;

  private final OAuth2RestTemplate oAuth2RestTemplate;

  @Autowired
  public AccountForeignService(OAuth2RestTemplate oAuth2RestTemplate) {
    this.oAuth2RestTemplate = oAuth2RestTemplate;
  }

  public AccountDto findById(Long id) {
    try {
      return oAuth2RestTemplate.getForEntity(accountUrl + "/{id}", AccountDto.class, id).getBody();
    } catch (Exception e) {
      throw new InternalServerException(e.getMessage());
    }
  }

}
