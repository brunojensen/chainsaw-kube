package de.chainsaw.app.account.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

  @Id
  private Long id;

  private String firstName;

  private String lastName;

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
