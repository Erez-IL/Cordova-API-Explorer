package com.emitrom.touch4j.app.client.ui.demos;

import com.emitrom.pilot.device.client.core.handlers.notification.ConfirmHandler;
import com.emitrom.pilot.device.client.notification.Notification;
import com.emitrom.pilot.util.client.core.Function;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.core.DemoPanel;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.layout.HBoxLayout;
import com.emitrom.touch4j.client.layout.VBoxLayout;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.FieldSet;
import com.emitrom.touch4j.client.ui.FormPanel;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.TextArea;
import com.google.gwt.user.client.Window;

public class NotificationDemo extends DemoPanel {

    private static final NotificationDemo INSTANCE = new NotificationDemo();

    public static NotificationDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new NotificationDemo();
        }
        return INSTANCE;
    }

    private TextArea textArea;

    private NotificationDemo() {
        FormPanel panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("navigator.notification");
        panel.add(fieldSet);

        if (Core.isPhone()) {
            fieldSet = new FieldSet("Default JavaScript alert");
        } else {
            fieldSet = new FieldSet("Click the button to see the default JavaScript alert");
        }

        panel.add(fieldSet);

        Panel buttonPanel = Core.createPanel();

        buttonPanel.add(new Button("JavaScript alert()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Window.alert("This is the default JavaScript alert");
            }
        }));
        panel.add(buttonPanel);

        if (Core.isPhone()) {
            fieldSet = new FieldSet("navigator.notification in action");
        } else {
            fieldSet = new FieldSet("Click the buttons below to see navigator.notification in action");
        }

        panel.add(fieldSet);

        if (Core.isPhone()) {
            buttonPanel = new Panel(new VBoxLayout());
        } else {
            buttonPanel = new Panel(new HBoxLayout());
        }

        Button b = new Button("alert()", UI.ACTION);
        b.setMargin(10);
        buttonPanel.add(b);
        b.addTapHandler(new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Notification.get().alert("Hello from Emtrom", new Function() {
                    @Override
                    public void execute() {
                        textArea.setValue("Alert dismissed");
                    }
                });
            }
        });
        buttonPanel.add(b);

        b = new Button("confirm()", UI.ACTION);
        b.setMargin(10);
        buttonPanel.add(b);
        b.addTapHandler(new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Notification.get().confirm("Touch4j rocks!", new ConfirmHandler() {
                    @Override
                    public void onConfirm(int button) {
                        textArea.setValue("Confirm dismissed. You pressed #: " + button);
                    }
                });
            }
        });
        buttonPanel.add(b);

        b = new Button("beep()", UI.ACTION);
        b.setMargin(10);
        buttonPanel.add(b);
        b.addTapHandler(new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Notification.get().beep(1);

            }
        });
        buttonPanel.add(b);

        b = new Button("vibrate()", UI.ACTION);
        b.setMargin(10);
        buttonPanel.add(b);
        b.addTapHandler(new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Notification.get().vibrate();
            }
        });
        buttonPanel.add(b);

        panel.add(buttonPanel);

        fieldSet = new FieldSet("Log");
        panel.add(fieldSet);

        textArea = new TextArea();
        panel.add(textArea);

        this.add(panel);
    }
}
