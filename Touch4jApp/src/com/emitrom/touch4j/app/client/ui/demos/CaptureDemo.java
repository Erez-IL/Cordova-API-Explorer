package com.emitrom.touch4j.app.client.ui.demos;

import java.util.ArrayList;

import com.emitrom.pilot.device.client.capture.Capture;
import com.emitrom.pilot.device.client.capture.CaptureError;
import com.emitrom.pilot.device.client.capture.MediaFile;
import com.emitrom.pilot.device.client.core.handlers.capture.CaptureHandler;
import com.emitrom.pilot.device.client.notification.Notification;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.core.DemoPanel;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.config.Dock;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Audio;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.FieldSet;
import com.emitrom.touch4j.client.ui.FormPanel;
import com.emitrom.touch4j.client.ui.MessageBox;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.Spacer;
import com.emitrom.touch4j.client.ui.ToolBar;
import com.emitrom.touch4j.client.ui.Video;
import com.emitrom.touch4j.client.utils.Features;
import com.google.gwt.user.client.Window;

public class CaptureDemo extends DemoPanel {

    private static final CaptureDemo INSTANCE = new CaptureDemo();

    public static CaptureDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new CaptureDemo();
        }
        return INSTANCE;
    }

    private FormPanel panel;
    private Audio audio;
    private Video video;
    private Panel buttonPanel;
    private Button playButton;
    private Panel displayPanel;
    private ToolBar toolBar;

    private CaptureDemo() {

        displayPanel = Core.createModalPanel();

        toolBar = new ToolBar(Dock.BOTTOM);
        toolBar.add(new Spacer());
        toolBar.add(new Button("Close", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                displayPanel.hide();
            }
        }));
        displayPanel.add(toolBar);

        panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("navigator.device.capture");
        panel.add(fieldSet);

        buttonPanel = Core.createPanel();

        Button b = new Button("captureAudio()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Capture.get().captureAudio(new CaptureHandler() {
                    @Override
                    public void onSucces(ArrayList<MediaFile> mediaFiles) {
                        audioSuccessHandler(mediaFiles);
                    }

                    @Override
                    public void onError(CaptureError error) {
                        errorHandler();
                    }
                });

            }
        });
        b.setMargin(10);
        buttonPanel.add(b);

        b = new Button("captureVideo()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Capture.get().captureVideo(new CaptureHandler() {
                    @Override
                    public void onSucces(ArrayList<MediaFile> mediaFiles) {
                        videoSuccessHandler(mediaFiles);
                    }

                    @Override
                    public void onError(CaptureError error) {
                        errorHandler();
                    }
                });

            }
        });
        b.setMargin(10);
        buttonPanel.add(b);
        panel.add(buttonPanel);
        this.add(panel);
    }

    private void errorHandler() {
        Notification.get().alert("Capture", "Error");
    }

    private void audioSuccessHandler(ArrayList<MediaFile> mediaFiles) {
        if (audio == null) {
            audio = new Audio();
            if (Features.get().os().isAndroid()) {
                audio.setEnableControls(false);
            }
            audio.setWidth("100%");
            displayPanel.add(audio);

            playButton = new Button("Play sound", UI.CONFIRM, new TapHandler() {
                @Override
                public void onTap(Button button, EventObject event) {
                    if (audio.isPlaying()) {
                        audio.pause();
                        playButton.setText("Play Audio");
                    } else {
                        audio.play();
                        playButton.setText("Pause Audio");
                    }
                }
            });
            toolBar.insert(1, playButton);
        }
        for (MediaFile mediaFile : mediaFiles) {
            String path = mediaFile.getFullPath();
            audio.setUrl(path);
        }
        displayPanel.show();

    }

    private void videoSuccessHandler(ArrayList<MediaFile> mediaFiles) {
        if (playButton != null) {
            playButton.setDisabled(true);
        }
        if (Features.get().has().video()) {
            if (video == null) {
                video = new Video();
                video.setEnableControls(true);
                video.setLoop(false);
                video.setWidth(Window.getClientWidth() - 20);
                video.setHeight(Window.getClientHeight() - 20);
                displayPanel.add(video);
            }
            for (MediaFile mediaFile : mediaFiles) {
                String path = mediaFile.getFullPath();
                video.setUrl(path);
            }
            displayPanel.show();

        } else {
            MessageBox.alert("Error", "The HTML5 <video> tag is not supported on this device.");
        }

    }
}
