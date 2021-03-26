package de.gematik.demis.entities;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public enum ADAPTER_Properties implements IProperties {
  DEBUG("debuginfo.enabled", VALUE_TYPE.BOOLEAN, false, "true", true, false),
  FHIR_BASEPATH(
      "fhir.basepath",
      VALUE_TYPE.SELECT_FIX_FHIR_BASEPATH,
      false,
      "https://demis.rki.de/notification-api/fhir/",
      false,
      false),
  IDP_LAB_TOKENENDPOINT(
      "idp.lab.tokenendpoint",
      VALUE_TYPE.URL,
      false,
      "https://demis.rki.de/auth/realms/LAB/protocol/openid-connect/token",
      true,
      false),
  IDP_LAB_CLIENTID("idp.lab.clientid", VALUE_TYPE.STRING, false, "demis-adapter", true, false),
  IDP_LAB_SECRET("idp.lab.secret", VALUE_TYPE.STRING, false, "secret_client_secret", true, false),
  IDP_LAB_PROXY("idp.lab.proxy", VALUE_TYPE.STRING, true, "", false, true),
  IDP_LAB_PROXY_HOST("idp.lab.proxy.host", VALUE_TYPE.STRING, true, "", false, false),
  IDP_LAB_PROXY_PORT("idp.lab.proxy.port", VALUE_TYPE.PORT, true, "", false, false),
  IDP_LAB_PROXY_USERNAME("idp.lab.proxy.username", VALUE_TYPE.STRING, true, "", false, false),
  IDP_LAB_PROXY_PASSWORD("idp.lab.proxy.password", VALUE_TYPE.PASSWORD, true, "", false, false),
  IDP_LAB_TRUSTSTORE(
      "idp.lab.truststore",
      VALUE_TYPE.RELATIVE_PATH_FILE,
      false,
      "../config/nginx.truststore",
      true,
      false),
  IDP_LAB_TRUSTSTOREPASSWORD(
      "idp.lab.truststorepassword", VALUE_TYPE.PASSWORD, false, "secret", true, false),
  LABOR_CONFIGFILE("labor.configfile", VALUE_TYPE.PATH_LIST, false, "", false, false),
  ;

  private final String key;
  private final VALUE_TYPE type;
  private final boolean optional;
  private final boolean expertValue;
  private final String defaultValue;
  private final boolean deprecated;

  ADAPTER_Properties(
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

  @Override
  public boolean isDeprecated() {
    return deprecated;
  }
}
