package com.emitrom.touch4j.app.client.data;

import java.util.ArrayList;
import java.util.List;

import com.emitrom.pilot.device.client.contacts.Contact;
import com.emitrom.touch4j.client.data.BaseModel;

/**
 * Represents a model for a contact
 * 
 */
public class ContactModel extends BaseModel {

    public static final String CONTACT_NAME = "name";
    public static final String CONTANCT_PHONE = "phone";

    ContactModel(String name, String phone) {
        setName(name);
        setPhone(phone);
    }

    private void setName(String name) {
        set(CONTACT_NAME, name);
    }

    private void setPhone(String phone) {
        set(CONTANCT_PHONE, phone);
    }

    public String getName() {
        return get(CONTACT_NAME);
    }

    public String getDescription() {
        return get(CONTANCT_PHONE);
    }

    public static List<ContactModel> from(List<Contact> contacts) {
        List<ContactModel> models = new ArrayList<ContactModel>();
        for (Contact c : contacts) {
            String phone = "N/A";
            if (!c.getPhoneNumbers().isEmpty()) {
                phone = c.getPhoneNumbers().get(0).getValue();
            }
            ContactModel model = new ContactModel(c.getDisplayName(), phone);
            models.add(model);
        }
        return models;
    }

}
