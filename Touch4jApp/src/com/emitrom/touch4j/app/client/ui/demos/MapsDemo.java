package com.emitrom.touch4j.app.client.ui.demos;

import com.emitrom.pilot.device.client.core.handlers.geolocation.GeoLocationHandler;
import com.emitrom.pilot.device.client.geolocation.GeoLocation;
import com.emitrom.pilot.device.client.geolocation.Position;
import com.emitrom.pilot.device.client.geolocation.PositionError;
import com.emitrom.pilot.device.client.notification.Notification;
import com.emitrom.pilot.maps.client.GMap;
import com.emitrom.pilot.maps.client.base.LatLng;
import com.emitrom.pilot.maps.client.core.MapTypeId;
import com.emitrom.pilot.maps.client.overlays.Marker;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.core.DemoPanel;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.config.Dock;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.FieldSet;
import com.emitrom.touch4j.client.ui.FormPanel;
import com.emitrom.touch4j.client.ui.GoogleMap;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.ToolBar;

public class MapsDemo extends DemoPanel {

    private static final MapsDemo INSTANCE = new MapsDemo();

    public static MapsDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new MapsDemo();
        }
        return INSTANCE;
    }

    private FormPanel panel;
    private Panel buttonPanel;
    private Panel mapPanel;
    private GoogleMap googleMap;
    private GMap map;
    private String watchId;
    private Button panelButton;

    private MapsDemo() {
        panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("navigator.geolocation");
        panel.add(fieldSet);

        buttonPanel = Core.createPanel();

        Button b = new Button("getCurrentPosition()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                GeoLocation.get().getCurrentLocation(new GeoLocationHandler() {

                    @Override
                    public void onSuccess(Position position) {
                        successHandler(position);
                        mapPanel.show();
                    }

                    @Override
                    public void onError(PositionError error) {
                        errorHandler(error);
                    }
                });

            }
        });
        b.setMargin(10);
        buttonPanel.add(b);

        b = new Button("watchPosition()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                if (watchId != null) {
                    Notification.get().alert("Geolocation", "You are already watching");
                } else {
                    watchId = GeoLocation.get().watchPosition(new GeoLocationHandler() {
                        @Override
                        public void onSuccess(Position position) {
                            successHandler(position);
                        }

                        @Override
                        public void onError(PositionError error) {
                            errorHandler(error);
                        }
                    });
                    panelButton.setText("Clearwatch");
                    panelButton.addTapHandler(new TapHandler() {
                        @Override
                        public void onTap(Button button, EventObject event) {
                            clearWatch();
                        }
                    });
                    mapPanel.show();
                }

            }
        });
        b.setMargin(10);
        buttonPanel.add(b);

        b = new Button("clearWatch()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                clearWatch();
            }
        });
        b.setMargin(10);
        buttonPanel.add(b);
        panel.add(buttonPanel);

        fieldSet = new FieldSet();
        panel.add(fieldSet);

        mapPanel = Core.createModalPanel();

        ToolBar toolBar = new ToolBar();
        toolBar.setDocked(Dock.BOTTOM);
        panelButton = new Button("Close", UI.CONFIRM, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                mapPanel.hide();
            }
        });
        toolBar.add(panelButton);
        mapPanel.add(toolBar);

        googleMap = new GoogleMap();

        map = new GMap(googleMap.getMap());
        map.setZoom(6);
        map.setMapType(MapTypeId.ROADMAP);
        // map.setCenter(new LatLng(7.01667, 12.66667));
        mapPanel.add(googleMap);

        this.add(panel);

    }

    private void successHandler(Position position) {
        LatLng latLgn = new LatLng(position.getCoords().getLatitude(), position.getCoords().getLongitude());
        map.setCenter(latLgn);
        new Marker(map, latLgn);
    }

    private void errorHandler(PositionError error) {
        Notification.get().alert(
                        "Geolocation",
                        "Can 't get your current location. Make sure the geolocation is enabled for this app. Error :"
                                        + error.getMessage());
    }

    private void clearWatch() {
        if (watchId == null) {
            Notification.get().alert("Geolocation", "Nothing to clear");
        } else {
            GeoLocation.get().clearWatch(watchId);
            watchId = null;
        }
    }

}
