package com.emitrom.touch4j.app.client.ui.demos;

import java.util.List;

import com.emitrom.pilot.device.client.contacts.Contact;
import com.emitrom.pilot.device.client.contacts.ContactError;
import com.emitrom.pilot.device.client.contacts.ContactProperty;
import com.emitrom.pilot.device.client.contacts.Contacts;
import com.emitrom.pilot.device.client.core.handlers.contacts.ContactFindHandler;
import com.emitrom.pilot.device.client.notification.Notification;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.core.DemoPanel;
import com.emitrom.touch4j.app.client.data.ContactModel;
import com.emitrom.touch4j.client.core.EventObject;
import com.emitrom.touch4j.client.core.config.Dock;
import com.emitrom.touch4j.client.core.handlers.button.TapHandler;
import com.emitrom.touch4j.client.data.Store;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.FieldSet;
import com.emitrom.touch4j.client.ui.FormPanel;
import com.emitrom.touch4j.client.ui.ListDataView;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.ToolBar;
import com.emitrom.touch4j.client.ui.ViewPort;

public class ContactsDemo extends DemoPanel {

    private static final ContactsDemo INSTANCE = new ContactsDemo();
    private List<Contact> userContacts;

    public static ContactsDemo get() {
        // because on the phone we use the navigation view
        // create a new instance each time
        if (Core.isPhone()) {
            return new ContactsDemo();
        }
        return INSTANCE;
    }

    private FormPanel panel;
    private Panel buttonPanel;
    private Panel contactsPanel;
    private Store store;
    private ListDataView contactsList;

    private ContactsDemo() {
        panel = new FormPanel();

        FieldSet fieldSet = new FieldSet("navigator.contacts");
        panel.add(fieldSet);

        buttonPanel = Core.createPanel();

        Button b = new Button("getContacts()", UI.ACTION, new TapHandler() {
            @Override
            public void onTap(Button button, EventObject event) {
                Contacts.get().find(new ContactFindHandler() {
                    @Override
                    public void onSuccess(List<Contact> contacts) {
                        createList(contacts);
                    }

                    @Override
                    public void onError(ContactError error) {
                        Notification.get().alert("Contacts", error.getCode().getDescription());

                    }
                }, ContactProperty.DISPLAY_NAME, ContactProperty.PHONE_NUMBERS, ContactProperty.EMAILS);

            }
        });
        b.setMargin(10);
        buttonPanel.add(b);
        panel.add(buttonPanel);

        fieldSet = new FieldSet();

        panel.add(fieldSet);
        this.add(panel);

    }

    private void createList(List<Contact> contacts) {
        if (contacts == null || contacts.size() <= 0) {
            Notification.get().alert("Contacts", "No contacts found");
        } else {
            this.userContacts = contacts;
            List<ContactModel> models = ContactModel.from(contacts);
            store = new Store(models);
            contactsList = new ListDataView(Core.getContactTemplate(), store);

            contactsPanel = Core.createModalPanel();
            contactsPanel.add(contactsList);
            contactsPanel.setHideOnMaskTap(true);

            ToolBar toolBar = new ToolBar();
            toolBar.setDocked(Dock.TOP);
            toolBar.setTitle("Contacts");
            contactsPanel.add(toolBar);
            ViewPort.get().add(contactsPanel);
            contactsPanel.show();

        }

    }

}
