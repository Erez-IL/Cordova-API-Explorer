package com.emitrom.touch4j.app.client.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the list of the Cordova modules
 * 
 */
public class Modules {

    private Modules() {

    }

    public static List<Module> getList() {
        List<Module> modules = new ArrayList<Module>();
        modules.add(new Module("Accelerometer", "Get x, y, z device acceleration"));
        modules.add(new Module("Camera", "Take pictures from your app"));
        modules.add(new Module("Capture", "Sounds, pictures, and videos"));
        modules.add(new Module("Compass", "Get compass orientation"));
        modules.add(new Module("Connection", "Get network connection info"));
        modules.add(new Module("Contacts", "Find and modify contacts"));
        modules.add(new Module("Database", "Access a local database"));
        modules.add(new Module("Device", "General device information"));
        // modules.add(new Module("Event", "Handle app life cycle events"));
        modules.add(new Module("File", "Read and write local files"));
        modules.add(new Module("Geolocation", "Track your location"));
        // modules.add(new Module("Google Maps",
        // "Track your location on a map"));
        modules.add(new Module("Notification", "Display native alerts"));
        return modules;
    }

}
