package de.gematik.demis.entities;

import java.util.Properties;

public enum ADAPTER_Properties implements IProperties {
    DEBUG("debuginfo.enabled", VALUE_TYPE.BOOLEAN, false, "Debug"),
    FHIR_BASEPATH("fhir.basepath", VALUE_TYPE.URL, false, "Server-URL"),
    IDP_LAB_TOKENENDPOINT("idp.lab.tokenendpoint", VALUE_TYPE.URL, false, "IDP-URL"),
    IDP_LAB_CLIENTID("idp.lab.clientid", VALUE_TYPE.STRING, false, "demis-adapter"),
    IDP_LAB_SECRET("idp.lab.secret", VALUE_TYPE.STRING, false, "secret_client_secret"),
    IDP_LAB_PROXY("idp.lab.proxy", VALUE_TYPE.STRING, true, ""),
    IDP_LAB_TRUSTSTORE("idp.lab.truststore", VALUE_TYPE.RELATIVE_PATH, false, "../config/nginx.truststore"),
    IDP_LAB_TRUSTSTOREPASSWORD("idp.lab.truststorepassword", VALUE_TYPE.PASSWORD, false, "secret"),
    LABOR_CONFIGFILE("labor.configfile", VALUE_TYPE.PATH_LIST, false, ""),

    ;

    private String key;
    private VALUE_TYPE type;
    private boolean optional;
    private String defaultValue;

    ADAPTER_Properties(String key, VALUE_TYPE type, boolean optional, String defaultValue) {
        this.key = key;
        this.type = type;
        this.optional = optional;
        this.defaultValue = defaultValue;
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
        return ""; //TODO: load from Languagefile
    }

    @Override
    public String getToolTip() {
        return ""; //TODO: load from Languagefile
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    public static boolean containsProperties(Properties properties){
        int counter = 0;
        for (ADAPTER_Properties v: values()) {
            if (properties.containsKey(v.getKey())) {
                counter++;
            }
        }
        return counter > 5;
    }
}
