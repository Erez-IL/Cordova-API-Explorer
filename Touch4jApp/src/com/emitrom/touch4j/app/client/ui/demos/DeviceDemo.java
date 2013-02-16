package com.emitrom.touch4j.app.client.ui.demos;

import com.emitrom.pilot.device.client.core.Device;
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

public class DeviceDemo extends DemoPanel {

    private static final DeviceDemo INSTANCE = new DeviceDemo();

    public static DeviceDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new DeviceDemo();
        }
        return INSTANCE;
    }

    private FormPanel panel;
    private Panel buttonPanel;
    private Text<String> deviceName;
    private Text<String> cordovaName;
    private Text<String> platformName;
    private Text<String> uuIdName;
    private Text<String> versionName;

    private DeviceDemo() {
        panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("Device");
        panel.add(fieldSet);

        buttonPanel = Core.createPanel();

        Button b = new Button("getDeviceInfo()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                deviceName.setValue(Device.get().getName());
                cordovaName.setValue(Device.get().getCordovaVersion());
                platformName.setValue(Device.get().getPlatform());
                uuIdName.setValue(Device.get().getUuId());
                versionName.setValue(Device.get().getVersion());

            }
        });
        b.setMargin(10);
        buttonPanel.add(b);
        panel.add(buttonPanel);

        fieldSet = new FieldSet();

        deviceName = new Text<String>();
        deviceName.setLabelWidth(150);
        deviceName.setLabel("device.name");
        fieldSet.add(deviceName);

        platformName = new Text<String>();
        platformName.setLabelWidth(150);
        platformName.setLabel("device.platform");
        fieldSet.add(platformName);

        uuIdName = new Text<String>();
        uuIdName.setLabelWidth(150);
        uuIdName.setLabel("device.uuid");
        fieldSet.add(uuIdName);

        versionName = new Text<String>();
        versionName.setLabelWidth(150);
        versionName.setLabel("device.version");
        fieldSet.add(versionName);

        cordovaName = new Text<String>();
        cordovaName.setLabelWidth(150);
        cordovaName.setLabel("device.cordova");
        fieldSet.add(cordovaName);

        panel.add(fieldSet);
        this.add(panel);

    }

}
