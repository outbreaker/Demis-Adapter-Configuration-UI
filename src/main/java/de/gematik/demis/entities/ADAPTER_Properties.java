package de.gematik.demis.entities;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public enum ADAPTER_Properties implements IProperties {
  DEBUG("debuginfo.enabled", VALUE_TYPE.BOOLEAN, false, "true", true),
  FHIR_BASEPATH("fhir.basepath", VALUE_TYPE.SELECT_FIX_FHIR_BASEPATH, false, "https://demis.rki.de/notification-api/fhir/", false),
  IDP_LAB_TOKENENDPOINT("idp.lab.tokenendpoint", VALUE_TYPE.URL, false, "https://demis.rki.de/auth/realms/LAB/protocol/openid-connect/token", true),
  IDP_LAB_CLIENTID("idp.lab.clientid", VALUE_TYPE.STRING, false, "demis-adapter", true),
  IDP_LAB_SECRET("idp.lab.secret", VALUE_TYPE.STRING, false, "secret_client_secret", true),
  IDP_LAB_PROXY("idp.lab.proxy", VALUE_TYPE.STRING, true, "", false),
  IDP_LAB_TRUSTSTORE(
      "idp.lab.truststore", VALUE_TYPE.RELATIVE_PATH, false, "../config/nginx.truststore", true),
  IDP_LAB_TRUSTSTOREPASSWORD(
      "idp.lab.truststorepassword", VALUE_TYPE.PASSWORD, false, "secret", true),
  LABOR_CONFIGFILE("labor.configfile", VALUE_TYPE.PATH_LIST, false, "", false),
  ;

  private final String key;
  private final VALUE_TYPE type;
  private final boolean optional;
  private final boolean expertValue;
  private final String defaultValue;

  ADAPTER_Properties(
      String key, VALUE_TYPE type, boolean optional, String defaultValue, boolean expertValue) {
    this.key = key;
    this.type = type;
    this.optional = optional;
    this.expertValue = expertValue;
    this.defaultValue = defaultValue;
  }

  public static boolean containsProperties(Properties properties) {
    int counter = 0;
    for (ADAPTER_Properties v : values()) {
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
}
