package com.emitrom.touch4j.app.client.data;

import com.emitrom.touch4j.client.data.BaseModel;

/**
 * Represents a model in the app
 * 
 * 
 */
public class Model extends BaseModel {

    public static final String MODEL_VALUE = "modelValue";

    private void setModelValue(String value) {
        set(MODEL_VALUE, value);
    }

    Model(String value) {
        setModelValue(value);
    }

    public String getModelValue() {
        return get(MODEL_VALUE);
    }

}
