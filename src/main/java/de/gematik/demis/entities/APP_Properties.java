package de.gematik.demis.entities;

import java.util.Properties;

public enum APP_Properties implements IProperties {
  DEBUG("debuginfo.enabled", VALUE_TYPE.BOOLEAN, false, "Debug", true),
  FOLDER_INCOMING(
      "incoming.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/input", true),
  FOLDER_SUBMITTED(
      "submitted.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/done", true),
  FOLDER_ERROR("error.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/error", true),
  FOLDER_QUEUED(
      "queued.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/queue", true),

  VALID_DEMIS_JOKERS(
      "labor.ldt.valid9901",
      VALUE_TYPE.STRING_LIST,
      false,
      "demis_einsender_ansprechpartner,demis_einsender_telefon,demis_einsender_fax,demis_einsender_email,demis_test_code,demis_betroffeneperson_strasse,demis_betroffeneperson_hausnummer,demis_betroffeneperson_plz,demis_betroffeneperson_ort,demis_betroffeneperson_laendercode,demis_betroffeneperson_telefon\n",
      true),

  SENDRETRY_NBSECONDS("sendretry.nbseconds", VALUE_TYPE.INT, false, "", true),
  SENDRETRY_NBATTEMPTS("sendretry.nbattempts", VALUE_TYPE.INT, false, "", true),
  SENDRETRY_NBTHREADS("sendretry.nbthreads", VALUE_TYPE.INT, false, "", true),
  MAINTENANCE_WAITNBMINUTES("maintenance.waitnbminutes", VALUE_TYPE.INT, false, "5", true),
  ;

  private final boolean expertValue;
  private final String key;
  private final VALUE_TYPE type;
  private final boolean optional;
  private final String defaultValue;

  APP_Properties(
      String key, VALUE_TYPE type, boolean optional, String defaultValue, boolean expertValue) {
    this.key = key;
    this.type = type;
    this.optional = optional;
    this.expertValue = expertValue;
    this.defaultValue = defaultValue;
  }

  public static boolean containsProperties(Properties properties) {
    int counter = 0;
    for (APP_Properties v : values()) {
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
  public VALUE_TYPE getType() {
    return type;
  }

  @Override
  public boolean isOptional() {
    return optional;
  }

  @Override
  public String getDisplayName() {
    return ""; // TODO: load from Languagefile
  }

  @Override
  public String getToolTip() {
    return ""; // TODO: load from Languagefile
  }

  @Override
  public String getDefaultValue() {
    return defaultValue;
  }

  @Override
  public boolean isExpertValue() {
    return expertValue;
  }
}
