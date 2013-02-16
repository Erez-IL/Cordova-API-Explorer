package com.emitrom.touch4j.app.client.ui.demos;

import com.emitrom.pilot.device.client.camera.Camera;
import com.emitrom.pilot.device.client.camera.CameraOptions;
import com.emitrom.pilot.device.client.camera.DestinationType;
import com.emitrom.pilot.device.client.camera.EncodingType;
import com.emitrom.pilot.device.client.camera.PictureSourceType;
import com.emitrom.pilot.device.client.core.handlers.camera.CameraPictureHandler;
import com.emitrom.pilot.device.client.notification.Notification;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.core.DemoPanel;
import com.emitrom.touch4j.app.client.data.DataUtil;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.config.Dock;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.FieldSet;
import com.emitrom.touch4j.client.ui.FormPanel;
import com.emitrom.touch4j.client.ui.Image;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.Select;
import com.emitrom.touch4j.client.ui.Spacer;
import com.emitrom.touch4j.client.ui.ToolBar;
import com.google.gwt.user.client.Window;

public class CameraDemo extends DemoPanel {

    private static final CameraDemo INSTANCE = new CameraDemo();
    private Panel imagePanel;

    public static CameraDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new CameraDemo();
        }
        return INSTANCE;
    }

    private Select<String> source;
    private Select<String> quality;
    private Select<String> encoding;
    private FormPanel panel;
    private Image image;

    private CameraDemo() {

        imagePanel = Core.createModalPanel();
        panel = new FormPanel();

        image = new Image();
        image.setWidth(Window.getClientWidth() - 20);
        image.setHeight(Window.getClientHeight() - 20);

        ToolBar toolBar = new ToolBar(Dock.BOTTOM);
        toolBar.add(new Spacer());
        toolBar.add(new Button("Close", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                imagePanel.hide();
            }
        }));
        imagePanel.add(toolBar);

        FieldSet fieldSet = new FieldSet("navigator.camera");
        panel.add(fieldSet);

        Button b = new Button("getPicture()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                CameraOptions options = new CameraOptions();
                options.setQuality(Double.valueOf(quality.getValue()));
                options.setDestinationType(DestinationType.FILE_URI);
                options.setSourceType(PictureSourceType.fromValue(Integer.valueOf(source.getValue())));
                options.setEncodingType(EncodingType.fromValue(Integer.valueOf(encoding.getValue())));
                options.setTargetHeight(image.getHeight());
                options.setTargetWidth(image.getWidth());
                Camera.get().getPicture(new CameraPictureHandler() {
                    @Override
                    public void onSuccess(String imageData) {
                        successHandler(imageData);
                    }

                    @Override
                    public void onError(String message) {
                        errorHandler(message);

                    }
                }, options);
            }
        });
        b.setMargin(10);
        panel.add(b);

        fieldSet = new FieldSet();

        source = new Select<String>();
        source.setLabel("Source");
        source.setOptions(DataUtil.getSources());
        source.setValue("1");
        fieldSet.add(source);

        quality = new Select<String>();
        quality.setLabel("Quality");
        quality.setOptions(DataUtil.getQualities());
        quality.setValue("50");
        fieldSet.add(quality);

        encoding = new Select<String>();
        encoding.setLabel("Encoding");
        encoding.setOptions(DataUtil.getEncodings());
        encoding.setValue("0");
        fieldSet.add(encoding);

        panel.add(fieldSet);

        panel.add(image);

        this.add(panel);
    }

    private void errorHandler(String message) {
        Notification.get().alert("Camera", message);
    }

    private void successHandler(String imageData) {
        image.setSrc(imageData);
        imagePanel.add(image);
        imagePanel.show();
    }
}
