package de.chainsaw.app.bank.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BankDto {

  private Long id;

  private String firstName;

  private String lastName;

  private String iban;

  @JsonCreator
  public BankDto(
      @JsonProperty("id") Long id,
      @JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName,
      @JsonProperty("IBAN") String iban) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.iban = iban;
  }

  public BankDto() {}

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

  public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }
}
