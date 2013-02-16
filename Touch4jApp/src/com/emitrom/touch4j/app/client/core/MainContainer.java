package com.emitrom.touch4j.app.client.core;

import com.emitrom.pilot.device.client.notification.Notification;
import com.emitrom.touch4j.client.layout.FitLayout;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.ViewPort;

/**
 * Main container of the touch4j application.
 * 
 */
public class MainContainer extends Panel {

    private static final MainContainer INSTANCE = new MainContainer();

    private MainContainer() {
        this.setLayout(new FitLayout());
    }

    /**
     * returns the unique instance of the main container
     * 
     * @return
     */
    public static MainContainer get() {
        checkOrientation();
        return INSTANCE;
    }

    private static void checkOrientation() {
        if (!Core.isPhone() && ViewPort.get().getOrientation().equalsIgnoreCase("portrait")) {
            Notification.get().alert("Emitrom-Touch4j", "For a better experience please use the landscape orientation");
        }
    }

}
