package de.gematik.demis.entities;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public enum LABORATORY_JSON implements IJson {
  IDENTIFIKATOR("identifikator", false, "", false),
  POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN(
      "positiveTestergebnisBezeichnungen",
      false,
      "schwach nachweisbar,positiv,POSITIV,P O S I T I V",
      false),
  MELDER_PERSON("Melder-Person", false, "", false),
  VORNAME("vorname", false, "", false),
  NACHNAME("nachname", false, "", false),
  ANSCHRIFTENZEILE("anschriftenzeile", false, "", false),
  POSTLEITZAHL("postleitzahl", false, "", false),
  STADT("stadt", false, "", false),
  TELEFONNUMMER("telefonnummer", false, "", false),
  ERREICHBARKEIT("erreichbarkeit", false, "", false),
  MELDER_EINRICHTUNG("Melder-Einrichtung", false, "", false),
  BSNR("BSNR", false, "", false),
  NAME("name", false, "", false),
  EINRICHTUNGS_ART("einrichtungsArt", false, "laboratory", true),
  ANSPRECHSPARTNER_NACHNAME("ansprechspartnerNachname", false, "", false),
  ANSPRECHSPARTNER_VORNAME("ansprechspartnerVorname", false, "", false),
  FAXNUMMER("faxnummer", false, "", false),
  EMAIL("email", false, "", false),
  WEBSEITE("webseite", false, "", false),
  IDP("idp", false, "", true),
  USERNAME("username", false, "", true),
  AUTHCERTKEYSTORE("authcertkeystore", false, "", true),
  AUTHCERTPASSWORD("authcertpassword", false, "", true),
  AUTHCERTALIAS("authcertalias", false, "", true),
  LOAD_DATA("LOAD_DATA", false, "", false),
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
    var displayNames = ResourceBundle.getBundle("DisplayNamesBundle", Locale.getDefault());
    return displayNames.getString(this.getKey());
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
