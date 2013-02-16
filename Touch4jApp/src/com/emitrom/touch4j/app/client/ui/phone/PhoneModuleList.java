package com.emitrom.touch4j.app.client.ui.phone;

import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.data.Modules;
import com.emitrom.touch4j.client.data.Store;
import com.emitrom.touch4j.client.ui.DisclosureList;

public class PhoneModuleList extends DisclosureList {

    private static final PhoneModuleList INSTANCE = new PhoneModuleList(Core.getModuleListTemplate(), new Store(
                    Modules.getList()));

    public static PhoneModuleList get() {
        return INSTANCE;
    }

    protected PhoneModuleList(String itemTemplate, Store store) {
        super(itemTemplate, store);
        this.setListStyle(ListStyle.ROUND);
    }
}
