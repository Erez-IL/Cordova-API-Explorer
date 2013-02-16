package com.emitrom.touch4j.app.client.controller;

import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.ui.demos.AccelerometerDemo;
import com.emitrom.touch4j.app.client.ui.demos.CameraDemo;
import com.emitrom.touch4j.app.client.ui.demos.CaptureDemo;
import com.emitrom.touch4j.app.client.ui.demos.CompassDemo;
import com.emitrom.touch4j.app.client.ui.demos.ConnectionDemo;
import com.emitrom.touch4j.app.client.ui.demos.ContactsDemo;
import com.emitrom.touch4j.app.client.ui.demos.DataBaseDemo;
import com.emitrom.touch4j.app.client.ui.demos.DeviceDemo;
import com.emitrom.touch4j.app.client.ui.demos.FileDemo;
import com.emitrom.touch4j.app.client.ui.demos.MapsDemo;
import com.emitrom.touch4j.app.client.ui.demos.NotificationDemo;
import com.emitrom.touch4j.app.client.ui.phone.PhoneView;
import com.emitrom.touch4j.app.client.ui.tablet.TabletView;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.Scroller;
import com.emitrom.touch4j.client.core.config.Dock;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.laf.Direction;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.Container;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.Spacer;
import com.emitrom.touch4j.client.ui.ToolBar;
import com.google.gwt.user.client.ui.Frame;

/**
 * Application controller. Controls the flow in the app
 * 
 * 
 */
public class Controller {

    private static Panel displayPanel;
    private static Frame frame;

    static {
        getApiButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                displayApi(currentDispayDemo);
            }
        });
        displayPanel = Core.createModalPanel();
        displayPanel.setScroller(new Scroller(Direction.BOTH));
        frame = new Frame(Core.EMITROM_HOME);
        displayPanel.add(frame);
        ToolBar toolbar = new ToolBar(Dock.BOTTOM);
        toolbar.add(new Spacer());
        toolbar.add(new Button("Close", UI.CONFIRM, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                displayPanel.hide();
            }
        }));
        displayPanel.add(toolbar);
    }

    private Controller() {

    }

    private static String currentDispayDemo = "";

    public static void showApiButton() {
        getApiButton().show();
    }

    public static void hideApiButton() {
        getApiButton().hide();
    }

    public static void displayDemoFor(String demoName) {
        showApiButton();
        bindHandler(demoName);
        if (demoName.equalsIgnoreCase("notification")) {
            dispayDemo(NotificationDemo.get());
        } else if (demoName.equalsIgnoreCase("accelerometer")) {
            dispayDemo(AccelerometerDemo.get());
        } else if (demoName.equalsIgnoreCase("camera")) {
            dispayDemo(CameraDemo.get());
        } else if (demoName.equalsIgnoreCase("capture")) {
            dispayDemo(CaptureDemo.get());
        } else if (demoName.equalsIgnoreCase("compass")) {
            dispayDemo(CompassDemo.get());
        } else if (demoName.equalsIgnoreCase("connection")) {
            dispayDemo(ConnectionDemo.get());
        } else if (demoName.equalsIgnoreCase("device")) {
            dispayDemo(DeviceDemo.get());
        } else if (demoName.equalsIgnoreCase("geolocation")) {
            dispayDemo(MapsDemo.get());
        } else if (demoName.equalsIgnoreCase("contacts")) {
            dispayDemo(ContactsDemo.get());
        } else if (demoName.equalsIgnoreCase("file")) {
            dispayDemo(FileDemo.get());
        } else if (demoName.equalsIgnoreCase("database")) {
            dispayDemo(DataBaseDemo.get());
        }
    }

    private static void dispayDemo(Container container) {
        if (Core.isPhone()) {
            PhoneView.get().push(container);
        } else {
            TabletView.get().addDemo(container);
        }
    }

    private static void bindHandler(final String demoName) {
        currentDispayDemo = demoName;
    }

    private static Button getApiButton() {
        if (Core.isPhone()) {
            return PhoneView.get().getApiButton();
        }
        return TabletView.get().getApiButton();
    }

    private static void displayApi(String module) {
        String url = Core.CORDOVA_HOME + Core.CURRENT_SUPPORTED_VERSION + "/" + getModuleUrlName(module) + ".html";
        frame.setUrl(url);
        displayPanel.show();
    }

    private static String getModuleUrlName(String module) {
        StringBuilder builder = new StringBuilder();
        builder.append("cordova_").append(module.toLowerCase()).append("_").append(module.toLowerCase()).append(
                        ".md.html#").append(module);;
        return builder.toString();

    }
}
