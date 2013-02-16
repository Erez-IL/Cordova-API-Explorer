package com.emitrom.touch4j.app.client.ui.phone;

import com.emitrom.touch4j.app.client.controller.Controller;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.data.Module;
import com.emitrom.touch4j.client.core.handlers.dataview.DataViewItemTapHandler;
import com.emitrom.touch4j.client.data.BaseModel;
import com.emitrom.touch4j.client.layout.FitLayout;
import com.emitrom.touch4j.client.ui.DataView;
import com.emitrom.touch4j.client.ui.Panel;
import com.google.gwt.user.client.Element;

/**
 * Implements the start page for the phone
 * 
 * 
 */
public class PhoneStarPage extends Panel {

    private static final PhoneStarPage INSTANCE = new PhoneStarPage();

    public static PhoneStarPage get() {
        return INSTANCE;
    }

    private PhoneStarPage() {
        this.setTitle(Core.getAppTitle());
        this.setLayout(new FitLayout());
        this.add(PhoneModuleList.get());
        PhoneModuleList.get().addItemTapHandler(new DataViewItemTapHandler() {
            @Override
            public void onItemTap(DataView dataView, int index, Element element, BaseModel record, Object eventObject,
                            Object eOpts) {
                Controller.displayDemoFor(record.get(Module.MODULE_NAME));

            }
        });
    }

}
