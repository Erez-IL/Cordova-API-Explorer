package com.emitrom.touch4j.app.client.ui.demos;

import com.emitrom.pilot.device.client.connection.Connection;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.core.DemoPanel;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.FieldSet;
import com.emitrom.touch4j.client.ui.FormPanel;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.Text;

public class ConnectionDemo extends DemoPanel {

    private static final ConnectionDemo INSTANCE = new ConnectionDemo();

    public static ConnectionDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new ConnectionDemo();
        }
        return INSTANCE;
    }

    private FormPanel panel;
    private Panel buttonPanel;
    private Text<String> connectionType;

    private ConnectionDemo() {
        panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("navigator.network.connection");
        panel.add(fieldSet);

        buttonPanel = Core.createPanel();

        Button b = new Button("getConnectionType()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                connectionType.setValue(Connection.get().getType().getValue());
            }
        });
        b.setMargin(10);
        buttonPanel.add(b);
        panel.add(buttonPanel);

        fieldSet = new FieldSet();

        connectionType = new Text<String>();
        connectionType.setLabelWidth(150);
        connectionType.setLabel("ConnectionType");

        fieldSet.add(connectionType);
        panel.add(fieldSet);
        this.add(panel);

    }

}
