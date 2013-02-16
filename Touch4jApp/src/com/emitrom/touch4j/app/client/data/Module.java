package com.emitrom.touch4j.app.client.data;

import com.emitrom.touch4j.client.data.BaseModel;

/**
 * Represents a cordova module
 * 
 */
public class Module extends BaseModel {

    public static final String MODULE_NAME = "name";
    public static final String MODULE_DESCRIPTION = "description";

    Module(String name, String description) {
        setName(name);
        setDescription(description);
    }

    private void setName(String name) {
        set(MODULE_NAME, name);
    }

    private void setDescription(String description) {
        set(MODULE_DESCRIPTION, description);
    }

    public String getName() {
        return get(MODULE_NAME);
    }

    public String getDescription() {
        return get(MODULE_DESCRIPTION);
    }

}
