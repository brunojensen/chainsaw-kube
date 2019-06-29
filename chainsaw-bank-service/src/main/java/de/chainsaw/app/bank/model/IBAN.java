package de.chainsaw.app.bank.model;

public final class IBAN {

  private static final String IBAN_BASE = "DE89370400440532013087";

  private IBAN() {}

  public static String resolve(Long bankAccountId) {
    final String id = String.valueOf(bankAccountId);
    return IBAN_BASE.substring(0, IBAN_BASE.length() - id.length()) + id;
  }

}
