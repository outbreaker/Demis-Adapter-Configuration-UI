package de.gematik.demis.entities;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public enum APP_Properties implements IProperties {
  DEBUG("debuginfo.enabled", VALUE_TYPE.BOOLEAN, false, "true", true, false),
  FOLDER_INCOMING(
      "incoming.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/input", true, false),
  FOLDER_SUBMITTED(
      "submitted.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/done", true, false),
  FOLDER_ERROR(
      "error.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/error", true, false),
  FOLDER_QUEUED(
      "queued.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/queue", true, false),

  VALID_DEMIS_JOKERS(
      "labor.ldt.valid9901",
      VALUE_TYPE.STRING_LIST,
      false,
      "demis_einsender_ansprechpartner,demis_einsender_telefon,demis_einsender_fax,demis_einsender_email,demis_test_code,demis_betroffeneperson_strasse,demis_betroffeneperson_hausnummer,demis_betroffeneperson_plz,demis_betroffeneperson_ort,demis_betroffeneperson_laendercode,demis_betroffeneperson_telefon\n",
      true,
      false),

  SENDRETRY_NBSECONDS("sendretry.nbseconds", VALUE_TYPE.INT, false, "60", true, false),
  SENDRETRY_NBATTEMPTS("sendretry.nbattempts", VALUE_TYPE.INT, false, "10", true, false),
  SENDRETRY_NBTHREADS("sendretry.nbthreads", VALUE_TYPE.INT, false, "10", true, false),
  MAINTENANCE_WAITNBMINUTES("maintenance.waitnbminutes", VALUE_TYPE.INT, false, "5", true, false),
  LABOR_LDT_GEBURTSDATUM_FORMAT(
      "labor.ldt.geburtsdatum.format", VALUE_TYPE.STRING, false, "yyyyMMdd", true, false),
  ;

  private final boolean expertValue;
  private final String key;
  private final VALUE_TYPE type;
  private final boolean optional;
  private final String defaultValue;
  private final boolean deprecated;

  APP_Properties(
      String key,
      VALUE_TYPE type,
      boolean optional,
      String defaultValue,
      boolean expertValue,
      boolean deprecated) {
    this.key = key;
    this.type = type;
    this.optional = optional;
    this.expertValue = expertValue;
    this.defaultValue = defaultValue;
    this.deprecated = deprecated;
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

  @Override
  public boolean isExpertValue() {
    return expertValue;
  }

  @Override
  public boolean isDeprecated() {
    return deprecated;
  }
}
