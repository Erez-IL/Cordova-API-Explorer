package com.emitrom.touch4j.app.client.ui.demos;

import com.emitrom.pilot.device.client.compass.Compass;
import com.emitrom.pilot.device.client.compass.CompassError;
import com.emitrom.pilot.device.client.compass.CompassHeading;
import com.emitrom.pilot.device.client.compass.CompassOptions;
import com.emitrom.pilot.device.client.core.handlers.compass.CompassHandler;
import com.emitrom.pilot.device.client.notification.Notification;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.core.DemoPanel;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.FieldSet;
import com.emitrom.touch4j.client.ui.FormPanel;
import com.emitrom.touch4j.client.ui.Image;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.Text;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;

public class CompassDemo extends DemoPanel {

    private static final CompassDemo INSTANCE = new CompassDemo();

    public static CompassDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new CompassDemo();
        }
        return INSTANCE;
    }

    private FormPanel panel;
    private Panel buttonPanel;
    private Text<String> magneticHeading;
    private Text<String> trueHeading;
    private Text<String> headingAccuracy;
    private Text<String> timeStamp;
    private Image image;
    private int watchId = -1;

    private CompassDemo() {

        panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("navigator.compass");
        panel.add(fieldSet);

        buttonPanel = Core.createPanel();

        Button b = new Button("getCurrentHeading()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Compass.get().getCurrentHeading(new CompassHandler() {
                    @Override
                    public void onSuccess(CompassHeading heading) {
                        sucessHandler(heading);
                    }

                    @Override
                    public void onError(CompassError error) {
                        errorHandler(error);
                    }
                });
            }
        });
        b.setMargin(10);
        buttonPanel.add(b);

        b = new Button("watchHeading()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                if (watchId >= 0) {
                    Notification.get().alert("Compass", "You are already watching");
                } else {
                    CompassOptions options = new CompassOptions();
                    options.setFrequency(1);
                    watchId = Compass.get().watchHeading(new CompassHandler() {
                        @Override
                        public void onSuccess(CompassHeading heading) {
                            sucessHandler(heading);
                        }

                        @Override
                        public void onError(CompassError error) {
                            errorHandler(error);
                        }
                    }, options);
                }

            }
        });
        b.setMargin(10);
        buttonPanel.add(b);

        b = new Button("clearWatch()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                if (watchId < 0) {
                    Notification.get().alert("Compass", "Nothing to clear");
                } else {
                    Compass.get().clearWatch(watchId);
                    watchId = -1;
                }

            }
        });
        b.setMargin(10);
        buttonPanel.add(b);
        panel.add(buttonPanel);

        fieldSet = new FieldSet();

        magneticHeading = new Text<String>();
        magneticHeading.setLabelWidth(150);
        magneticHeading.setLabel("magneticHeading");
        fieldSet.add(magneticHeading);

        trueHeading = new Text<String>();
        trueHeading.setLabel("trueHeading");
        trueHeading.setLabelWidth(150);
        fieldSet.add(trueHeading);

        headingAccuracy = new Text<String>();
        headingAccuracy.setLabel("headingAccuracy");
        headingAccuracy.setLabelWidth(150);
        fieldSet.add(headingAccuracy);

        timeStamp = new Text<String>();
        timeStamp.setLabel("timestamp");
        timeStamp.setLabelWidth(150);
        fieldSet.add(timeStamp);
        panel.add(fieldSet);

        image = new Image();
        image.setSrc(Core.COMPASS);
        image.setMargin(10);
        image.getElement().getStyle().setBorderColor("black");
        image.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
        image.getElement().getStyle().setBorderWidth(1, Unit.PX);
        image.setWidth(303);
        image.setHeight(300);

        panel.add(image);
        this.add(panel);

    }

    private void errorHandler(CompassError error) {
        Notification.get().alert("Compass Error", "Code : " + error.getCode() + "\n Message : " + error.getMessage());
    }

    private void sucessHandler(CompassHeading heading) {
        magneticHeading.setValue(heading.getMagneticHeading() + "");
        trueHeading.setValue(heading.getTrueHeading() + "");
        headingAccuracy.setValue(heading.getHeadingAccuracy() + "");
        timeStamp.setValue(heading.getTimeStamp() + "");
        image.setFlex(0);

        String rotate = "rotate(" + Math.round(heading.getMagneticHeading()) + "deg)";
        image.getElement().getStyle().setProperty("transform", rotate);
        image.getElement().getStyle().setProperty("-moz-transform", "(42deg)");
        image.getElement().getStyle().setProperty("-o-transform", "(42deg)");
        image.getElement().getStyle().setProperty("-webkit-transform", rotate);

    }

}
