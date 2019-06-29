package de.chainsaw.app.account.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public class AccountDto {

  private Long id;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @JsonCreator
  public AccountDto(
      @JsonProperty("id") Long id,
      @JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public AccountDto() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
