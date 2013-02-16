package com.emitrom.touch4j.app.client.core;

import com.emitrom.touch4j.app.client.data.ContactModel;
import com.emitrom.touch4j.app.client.data.Module;
import com.emitrom.touch4j.client.fx.layout.card.Animation;
import com.emitrom.touch4j.client.fx.layout.card.AnimationType;
import com.emitrom.touch4j.client.fx.layout.card.Direction;
import com.emitrom.touch4j.client.layout.FitLayout;
import com.emitrom.touch4j.client.layout.HBoxLayout;
import com.emitrom.touch4j.client.layout.VBoxLayout;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.ViewPort;
import com.emitrom.touch4j.client.utils.Features;
import com.google.gwt.user.client.Window;

/**
 * Core class. Implements some core methods for the Touch4jApp
 * 
 */
public class Core {

    public static final String LOGO = "./assets/emitrom_logo.png";
    public static final String COMPASS = "./assets/compass.png";
    public static final String SQL = "DROP TABLE IF EXISTS emplyee;CREATE TABLE em";
    public static final String EMITROM_HOME = "http://www.emitrom.com";
    public static final String CORDOVA_HOME = "http://docs.phonegap.com/en/";
    public static final String CURRENT_SUPPORTED_VERSION = "2.0.0";

    private Core() {

    }

    /**
     * return the application title based on the current device
     * 
     * @return
     */
    public static final String getAppTitle() {
        String TITLE = "";
        if (isPhone()) {
            TITLE = "Cordova Live API Explorer";
        } else {
            TITLE = "Cordova Live API Explorer";
        }
        return TITLE;
    }

    public static final String getModuleListTemplate() {
        String template = "";
        template += "<div class='content'>";
        template += "<div class='module_name'>";
        template += "{" + Module.MODULE_NAME + "}";
        template += "</div>";
        template += "<div class='module_description'>";
        template += "{" + Module.MODULE_DESCRIPTION + "}";
        template += "</div>";
        template += "</div>";
        return template;
    }

    public static String getSql() {
        String sql = "DROP TABLE IF EXISTS employee;\r\n";
        sql += "CREATE TABLE employee (id integer primary key autoincrement, firstname, lastname);\r\n";
        sql += "INSERT INTO employee (firstname, lastname) VALUES ('John', 'Smith');\r\n";
        sql += "INSERT INTO employee (firstname, lastname) VALUES ('Lisa', 'Jones');\r\n";

        return sql;
    }

    public static final String getContactTemplate() {
        String template = "";
        template += "<div class='content'>";
        template += "<div class='module_name'>";
        template += "{" + ContactModel.CONTACT_NAME + "}";
        template += "</div>";
        template += "<div class='module_description'>";
        template += "{" + ContactModel.CONTANCT_PHONE + "}";
        template += "</div>";
        template += "</div>";
        return template;
    }

    public static String getWelcomeText() {
        String WELCOME_TEXT = "<div id='welcome_div'><h2 class='welcome_text'>Select a module in the list to experiment with the API";
        WELCOME_TEXT += " </h2><h2 class='welcome_text'>Tap the API button in the upper right corner to access the API reference</h2></div>";
        return WELCOME_TEXT;
    }

    public static boolean isPhone() {
        return Features.get().os().isPhone();
    }

    public static Panel createPanel() {
        if (Core.isPhone()) {
            return new Panel(new VBoxLayout());
        } else {
            return new Panel(new HBoxLayout());
        }

    }

    public static Panel createHBoxPanel() {
        return new Panel(new HBoxLayout());
    }

    public static Panel createModalPanel() {
        Panel modalPanel = new Panel();
        modalPanel.setWidth(Window.getClientWidth() - 20);
        modalPanel.setHeight("70%");
        modalPanel.setLayout(new FitLayout());
        modalPanel.setHidden(true);
        modalPanel.setCentered(true);
        modalPanel.setModal(true);
        modalPanel.setShowAnimation(new Animation(AnimationType.SLIDE_IN, Direction.DOWN));
        modalPanel.setHideAnimation(new Animation(AnimationType.SLIDE_OUT, Direction.DOWN));
        ViewPort.get().add(modalPanel);
        return modalPanel;
    }

}
