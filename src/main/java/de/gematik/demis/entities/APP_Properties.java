package de.gematik.demis.entities;

import java.util.Properties;

public enum APP_Properties implements IProperties {
    DEBUG("debuginfo.enabled", VALUE_TYPE.BOOLEAN, false, "Debug"),
    FOLDER_INCOMING("incoming.lab.results.folder", VALUE_TYPE.RELATIVE_PATH,false,"../data/input"),
    FOLDER_SUBMITTED("submitted.lab.results.folder", VALUE_TYPE.RELATIVE_PATH,false, "../data/done"),
    FOLDER_ERROR("error.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/error"),
    FOLDER_QUEUED("queued.lab.results.folder", VALUE_TYPE.RELATIVE_PATH, false, "../data/queue"),

    VALID_DEMIS_JOKERS("labor.ldt.valid9901", VALUE_TYPE.STRING_LIST, false, "demis_einsender_ansprechpartner,demis_einsender_telefon,demis_einsender_fax,demis_einsender_email,demis_test_code,demis_betroffeneperson_strasse,demis_betroffeneperson_hausnummer,demis_betroffeneperson_plz,demis_betroffeneperson_ort,demis_betroffeneperson_laendercode,demis_betroffeneperson_telefon\n"),

    SENDRETRY_NBSECONDS("sendretry.nbseconds", VALUE_TYPE.INT, false, ""),
    SENDRETRY_NBATTEMPTS("sendretry.nbattempts", VALUE_TYPE.INT, false, ""),
    SENDRETRY_NBTHREADS("sendretry.nbthreads", VALUE_TYPE.INT, false, ""),
    MAINTENANCE_WAITNBMINUTES("maintenance.waitnbminutes", VALUE_TYPE.INT, false, "5"),
    ;

    private String key;
    private VALUE_TYPE type;
    private boolean optional;
    private String defaultValue;

    APP_Properties(String key, VALUE_TYPE type, boolean optional, String defaultValue) {
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
        for (APP_Properties v: values()) {
            if (properties.containsKey(v.getKey())) {
                counter++;
            }
        }
        return counter > 5;
    }
}
