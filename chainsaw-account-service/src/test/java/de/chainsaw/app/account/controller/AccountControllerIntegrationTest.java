package de.chainsaw.app.account.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.chainsaw.app.account.dto.AccountDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.GenericWebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerIntegrationTest {

  @Autowired
  private GenericWebApplicationContext webApplicationContext;

  private MockMvc mockServer;

  private ObjectMapper mapper = new ObjectMapper();

  @Before
  public void init() {
    mockServer = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testCreateAccount_Success() throws Exception {
    final AccountDto payload = new AccountDto();
    payload.setFirstName("First-1");
    payload.setLastName("Last-1");
    mockServer.perform(
        post("/account/1")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(payload)))
        .andExpect(status().isNoContent());

  }

  @Test
  public void testCreateAccount_InvalidRequest() throws Exception {
    mockServer.perform(
        post("/account/1")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content("{}"))
        .andExpect(status().isBadRequest());

  }

  @Test
  public void testCreateAccount_BadRequest() throws Exception {
    // Prepare first insertion
    final AccountDto payload = new AccountDto();
    payload.setFirstName("First-2");
    payload.setLastName("Last-2");
    mockServer.perform(
        post("/account/2")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(payload)))
        .andExpect(status().isNoContent());

    // Try again with the same
    mockServer.perform(
        post("/account/2")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(payload)))
        .andExpect(status().isBadRequest());

  }

  @Test
  public void testFindAll_Success() throws Exception {

    final MvcResult mvcResult = mockServer.perform(get("/accounts/"))
        .andExpect(status().isOk())
        .andReturn();

    final JsonNode jsonNode = mapper
        .readTree(mvcResult.getResponse().getContentAsString());

    assertThat(jsonNode.size()).isNotZero();
  }

  @Test
  public void testFindById_Success() throws Exception {

    final MvcResult mvcResult = mockServer.perform(
        get("/account/1"))
        .andExpect(status().isOk())
        .andReturn();

    final JsonNode jsonNode = mapper
        .readTree(mvcResult.getResponse().getContentAsString());

    assertThat(jsonNode.get("id").longValue()).isEqualTo(1L);
    assertThat(jsonNode.get("firstName").textValue()).isEqualTo("First-1");
    assertThat(jsonNode.get("lastName").textValue()).isEqualTo("Last-1");
  }

  @Test
  public void testFindById_NotFound() throws Exception {
    mockServer.perform(
        get("/account/123"))
        .andExpect(status().isNotFound());

  }
}
