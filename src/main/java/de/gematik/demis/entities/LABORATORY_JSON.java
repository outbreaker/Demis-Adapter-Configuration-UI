package de.gematik.demis.entities;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public enum LABORATORY_JSON implements IJson {
  IDENTIFIKATOR("identifikator", false, "123456789", false),
  POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN(
      "positiveTestergebnisBezeichnungen",
      false,
      "[\"schwach nachweisbar\", \"positiv\", \"POSITIV\", \"P O S I T I V\"]",
      false),
  MELDER_PERSON("Melder-Person", false, "", false),
  VORNAME("vorname", false, "MelderPersonTesVorname", false),
  NACHNAME("nachname", false, "MelderPersonTestNachname", false),
  ANSCHRIFTENZEILE("anschriftenzeile", false, "MelderPerson Strasse nr 123 Drittenhinterhof", false),
  POSTLEITZAHL("postleitzahl", false, "13055", false),
  STADT("stadt", false, "Berlin", false),
  TELEFONNUMMER("telefonnummer", false, "030 1234", false),
  ERREICHBARKEIT("erreichbarkeit", false, "montag-freitag 8:00-16:00", false),
  MELDER_EINRICHTUNG("Melder-Einrichtung", false, "", false),
  BSNR("BSNR", false, "123456789", false),
  NAME("name", false, "Laborarzt SARS", false),
  EINRICHTUNGS_ART("einrichtungsArt", false, "laboratory", false),
  ANSPRECHSPARTNER_NACHNAME("ansprechspartnerNachname", false, "MelderEinrichtung Ansp. Nachname", false),
  ANSPRECHSPARTNER_VORNAME("ansprechspartnerVorname", false, "MelderEinrichtung Ansp. Vorname", false),
  FAXNUMMER("faxnummer", false, "030 1234", false),
  EMAIL("email", false, "office@meldereinrichtung.de", false),
  WEBSEITE("webseite", false, "meldereinrichtung.de", false),
  IDP("idp", false, "", true),
  USERNAME("username", false, "", true),
  AUTHCERTKEYSTORE("authcertkeystore", false, "PleaseSet", true),
  AUTHCERTPASSWORD("authcertpassword", false, "../config/user.jks", true),
  AUTHCERTALIAS("authcertalias", false, "PleaseSetKeyStorePassword", true),
  LOAD_DATA("LOAD_DATA", false, "PleaseSet", false),
  ;

  private final String key;
  private final boolean optional;
  private final boolean expertValue;
  private final String defaultValue;

  LABORATORY_JSON(String key, boolean optional, String defaultValue, boolean expertValue) {
    this.key = key;
    this.optional = optional;
    this.expertValue = expertValue;
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
    return getKey(); // TODO: load from Languagefile
  }

  @Override
  public String getToolTip() {
    var tooltips = ResourceBundle.getBundle("TooltipsBundle", Locale.getDefault());
    return tooltips.getString(this.getKey());
  }

  @Override
  public String getDefaultValue() {
    return defaultValue;
  }

  public boolean isExpertValue() {
    return expertValue;
  }
}
