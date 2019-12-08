package de.chainsaw.app.bank.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.chainsaw.app.bank.dto.AccountDto;
import de.chainsaw.app.testsupport.OAuthTestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.GenericWebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = OAuthTestConfiguration.class)
@ActiveProfiles("test")
public class BankControllerIntegrationTest {

  @Autowired
  private OAuth2RestTemplate oAuth2RestTemplate;

  @Autowired
  private GenericWebApplicationContext webApplicationContext;

  private MockMvc mockServer;

  private MockRestServiceServer mockRestServiceServer;

  private ObjectMapper mapper = new ObjectMapper();

  @Before
  public void init() throws Exception {
    mockServer = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    mockRestServiceServer = MockRestServiceServer.createServer(oAuth2RestTemplate);
  }

  @Test
  public void testGetBankAccount() throws Exception {

    mockExternalRestCall();

    final MvcResult mvcResult = mockServer.perform(
        get("/bank/1"))
        .andExpect(status().isOk())
        .andReturn();

    final JsonNode jsonNode = mapper
        .readTree(mvcResult.getResponse().getContentAsString());

    assertThat(jsonNode.get("IBAN").textValue()).isEqualTo("DE89370400440532013081");
    assertThat(jsonNode.get("id").longValue()).isEqualTo(1L);
    assertThat(jsonNode.get("firstName").textValue()).isEqualTo("First-1");
    assertThat(jsonNode.get("lastName").textValue()).isEqualTo("Last-1");
  }

  @Test
  public void testGetBankAccount_handleError() throws Exception {
    mockExternalRestCallFailure();

    mockServer.perform(
        get("/bank/9"))
        .andExpect(status().isInternalServerError());

  }


  /**
   * Could be replaced for Spring Cloud Contracts.
   */
  private void mockExternalRestCall() throws Exception {
    mockRestServiceServer.reset();

    final AccountDto dto = new AccountDto();
    dto.setId(1L);
    dto.setFirstName("First-1");
    dto.setLastName("Last-1");

    mockRestServiceServer
        .expect(requestToUriTemplate("/EMPTY/1"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(mapper.writeValueAsString(dto), MediaType.APPLICATION_JSON));
  }

  /**
   * Could be replaced for Spring Cloud Contracts.
   */
  private void mockExternalRestCallFailure() throws Exception {
    mockRestServiceServer.reset();

    mockRestServiceServer
        .expect(requestToUriTemplate("/EMPTY/9"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withServerError());
  }
}
