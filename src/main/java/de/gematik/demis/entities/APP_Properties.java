package de.gematik.demis.entities;

public enum APP_Properties {
    DEBUG("debuginfo.enabled", VALUE_TYPE.BOOLEAN),
    FOLDER_INCOMING("incoming.lab.results.folder", VALUE_TYPE.RELATIVE_PATH),
    FOLDER_SUBMITTED("submitted.lab.results.folder", VALUE_TYPE.RELATIVE_PATH),
    FOLDER_ERROR("error.lab.results.folder", VALUE_TYPE.RELATIVE_PATH),
    FOLDER_QUEUED("queued.lab.results.folder", VALUE_TYPE.RELATIVE_PATH),

    VALID_DEMIS_JOKERS("labor.ldt.valid9901", VALUE_TYPE.STRING_LIST),

    SENDRETRY_NBSECONDS("sendretry.nbseconds", VALUE_TYPE.INT),
    SENDRETRY_NBATTEMPTS("sendretry.nbattempts", VALUE_TYPE.INT),
    SENDRETRY_NBTHREADS("sendretry.nbthreads", VALUE_TYPE.INT),
    MAINTENANCE_WAITNBMINUTES("maintenance.waitnbminutes", VALUE_TYPE.INT),
    ;

    private String key;
    private VALUE_TYPE type;

    APP_Properties(String key, VALUE_TYPE type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public VALUE_TYPE getType() {
        return type;
    }
}
