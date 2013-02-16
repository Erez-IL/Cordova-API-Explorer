package com.emitrom.touch4j.app.client;

import com.emitrom.touch4j.app.client.core.Display;
import com.emitrom.touch4j.client.core.TouchEntryPoint;

/**
 * Entry point of the Touch4j app.
 */

public class Touch4jApp extends TouchEntryPoint {

    @Override
    public void onLoad() {
        Display.getUi();
    }
}