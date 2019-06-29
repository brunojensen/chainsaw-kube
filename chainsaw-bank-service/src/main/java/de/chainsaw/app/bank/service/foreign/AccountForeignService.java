package de.chainsaw.app.bank.service.foreign;

import de.chainsaw.app.bank.exception.InternalServerException;
import de.chainsaw.app.bank.dto.AccountDto;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountForeignService {

  private RestTemplate accountRestTemplate;

  @Autowired
  public AccountForeignService(RestTemplate accountRestTemplate) {
    this.accountRestTemplate = accountRestTemplate;
  }

  public AccountDto findById(Long id) {
    try {
      final MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
      header.put("Authorization", Arrays.asList("Bearer " + getToken()));
      return accountRestTemplate.exchange("/{id}",
          HttpMethod.GET,
          new HttpEntity<>(header),
          AccountDto.class,
          id).getBody();
    } catch (Exception e) {
      throw new InternalServerException(e.getMessage());
    }
  }

  private static String getToken() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication
        .getDetails() instanceof OAuth2AuthenticationDetails) {
      return ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
    }
    throw new InternalServerException("Could not get token");
  }
}
