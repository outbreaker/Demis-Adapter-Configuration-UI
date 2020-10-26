package de.gematik.demis.entities;

import java.util.Properties;

public enum ADAPTER_Properties implements IProperties {
  DEBUG("debuginfo.enabled", VALUE_TYPE.BOOLEAN, false, "Debug", true),
  FHIR_BASEPATH("fhir.basepath", VALUE_TYPE.SELECT_FIX_FHIR_BASEPATH, false, "Server-URL", false),
  IDP_LAB_TOKENENDPOINT("idp.lab.tokenendpoint", VALUE_TYPE.URL, false, "IDP-URL", true),
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
