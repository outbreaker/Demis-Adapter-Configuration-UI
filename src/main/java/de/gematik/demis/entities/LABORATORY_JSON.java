package de.gematik.demis.entities;

import java.util.Properties;

public enum LABORATORY_JSON implements IJson {
  IDENTIFIKATOR("identifikator", false, ""),
  POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN("positiveTestergebnisBezeichnungen", false, ""),
  MELDER_PERSON("Melder-Person", false, ""),
  VORNAME("vorname", false, ""),
  NACHNAME("nachname", false, ""),
  ANSCHRIFTENZEILE("anschriftenzeile", false, ""),
  POSTLEITZAHL("postleitzahl", false, ""),
  STADT("stadt", false, ""),
  TELEFONNUMMER("telefonnummer", false, ""),
  ERREICHBARKEIT("erreichbarkeit", false, ""),
  MELDER_EINRICHTUNG("Melder-Einrichtung", false, ""),
  BSNR("BSNR", false, ""),
  NAME("name", false, ""),
  EINRICHTUNGS_ART("einrichtungsArt", false, ""),
  ANSPRECHSPARTNER_NACHNAME("ansprechspartnerNachname", false, ""),
  ANSPRECHSPARTNER_VORNAME("ansprechspartnerVorname", false, ""),
  FAXNUMMER("faxnummer", false, ""),
  EMAIL("email", false, ""),
  WEBSEITE("webseite", false, ""),
  IDP("idp", false, ""),
  USERNAME("username", false, ""),
  AUTHCERTKEYSTORE("authcertkeystore", false, ""),
  AUTHCERTPASSWORD("authcertpassword", false, ""),
  AUTHCERTALIAS("authcertalias", false, ""),
  LOAD_DATA("LOAD_DATA", false, ""),
  ;

  private String key;
  private boolean optional;
  private String defaultValue;

  LABORATORY_JSON(String key, boolean optional, String defaultValue) {
    this.key = key;
    this.optional = optional;
    this.defaultValue = defaultValue;
  }

  public static boolean containsProperties(Properties properties) {
    int counter = 0;
    for (LABORATORY_JSON v : values()) {
      if (properties.containsKey(v.getKey())) {
        counter++;
      }
    }
    return counter > 5;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public boolean isOptional() {
    return optional;
  }

  @Override
  public String getDisplayName() {
    return getKey(); //TODO: load from Languagefile
  }

  @Override
  public String getToolTip() {
    return ""; //TODO: load from Languagefile
  }

  @Override
  public String getDefaultValue() {
    return defaultValue;
  }
}
