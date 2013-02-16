package com.emitrom.touch4j.app.client.ui.demos;

import com.emitrom.pilot.device.client.accelerometer.Acceleration;
import com.emitrom.pilot.device.client.accelerometer.Accelerometer;
import com.emitrom.pilot.device.client.accelerometer.AccelerometerOptions;
import com.emitrom.pilot.device.client.core.handlers.accelerometer.AccelerometerHandler;
import com.emitrom.pilot.device.client.notification.Notification;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.core.DemoPanel;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.core.handlers.field.text.TextChangeHandler;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.FieldSet;
import com.emitrom.touch4j.client.ui.FormPanel;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.Text;

public class AccelerometerDemo extends DemoPanel {

    private static final AccelerometerDemo INSTANCE = new AccelerometerDemo();

    public static AccelerometerDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new AccelerometerDemo();
        }
        return INSTANCE;
    }

    private Text<String> accelerationX;
    private Text<String> accelerationY;
    private Text<String> accelerationZ;
    private Text<String> frequency;
    private String watchId;

    private AccelerometerDemo() {
        FormPanel panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("navigator.accelerometer");
        panel.add(fieldSet);

        Panel buttonPanel = Core.createPanel();

        Button b = new Button("getCurrentAcceleration()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Accelerometer.get().getCurrentAcceleration(new AccelerometerHandler() {
                    @Override
                    public void onSuccess(Acceleration acceleration) {
                        successHandler(acceleration);
                    }

                    @Override
                    public void onError() {
                        errorHandler();
                    }
                });
            }
        });
        b.setMargin(3);
        buttonPanel.add(b);

        b = new Button("watchAcceleration()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                watchHandler();
            }
        });
        b.setMargin(3);
        buttonPanel.add(b);

        b = new Button("clearWatch()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                if (watchId != null) {
                    clearWatch();
                } else {
                    Notification.get().alert("Accelerometer", "Nothing to clear");
                }
            }
        });
        b.setMargin(3);
        buttonPanel.add(b);

        panel.add(buttonPanel);
        fieldSet = new FieldSet();

        accelerationX = new Text<String>();
        accelerationX.setLabel("acceleration.x");
        accelerationX.setLabelWidth(150);
        fieldSet.add(accelerationX);

        accelerationY = new Text<String>();
        accelerationY.setLabel("acceleration.y");
        accelerationY.setLabelWidth(150);
        fieldSet.add(accelerationY);

        accelerationZ = new Text<String>();
        accelerationZ.setLabel("acceleration.z");
        accelerationZ.setLabelWidth(150);
        fieldSet.add(accelerationZ);

        panel.add(fieldSet);

        fieldSet = new FieldSet("Watch options");
        panel.add(fieldSet);

        frequency = new Text<String>();
        frequency.setLabelWidth(150);
        frequency.setLabel("frequency(ms)");
        frequency.setValue("100");
        frequency.addOnChangeHandler(new TextChangeHandler() {
            @Override
            public void onChange(Text<String> text, Object oldValue, Object newValue, Object eOpts) {
                if (watchId != null) {
                    clearWatch();
                    watchHandler();
                }
            }
        });
        panel.add(frequency);
        this.add(panel);
    }

    private void errorHandler() {
        Notification.get().alert("Error");
    }

    private void successHandler(Acceleration acceleration) {
        accelerationX.setValue(acceleration.getX() + "");
        accelerationY.setValue(acceleration.getY() + "");
        accelerationZ.setValue(acceleration.getZ() + "");
    }

    private void watchHandler() {
        if (watchId != null) {
            Notification.get().alert("Accelerometer", "You are already watching");
        } else {
            AccelerometerOptions options = new AccelerometerOptions(Double.valueOf(frequency.getValue()));
            watchId = Accelerometer.get().watchAcceleration(new AccelerometerHandler() {

                @Override
                public void onSuccess(Acceleration acceleration) {
                    successHandler(acceleration);
                }

                @Override
                public void onError() {
                    errorHandler();
                }
            }, options);
        }
    }

    private void clearWatch() {
        Accelerometer.get().clearWatch(watchId);
        watchId = null;
    }
}
