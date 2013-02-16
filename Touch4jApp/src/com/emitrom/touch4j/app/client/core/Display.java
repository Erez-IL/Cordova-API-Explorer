package com.emitrom.touch4j.app.client.core;

import com.emitrom.touch4j.app.client.ui.phone.PhoneView;
import com.emitrom.touch4j.app.client.ui.tablet.TabletView;
import com.emitrom.touch4j.client.ui.ViewPort;

/**
 * Displays the UI
 */
public class Display {

    private Display() {

    }

    public static void getUi() {
        ViewPort vp = ViewPort.get();
        MainContainer mainContainer = MainContainer.get();
        if (!Core.isPhone()) {
            mainContainer.add(PhoneView.get());
        } else {
            mainContainer.add(TabletView.get());
        }
        vp.add(mainContainer);
    }
}
