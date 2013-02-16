package com.emitrom.touch4j.app.client.ui.common;

import com.emitrom.touch4j.app.client.controller.Controller;
import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.data.Module;
import com.emitrom.touch4j.app.client.data.Modules;
import com.emitrom.touch4j.client.core.handlers.dataview.DataViewItemTapHandler;
import com.emitrom.touch4j.client.data.BaseModel;
import com.emitrom.touch4j.client.data.Store;
import com.emitrom.touch4j.client.ui.DataView;
import com.emitrom.touch4j.client.ui.ListDataView;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

/**
 * List of cordova modules
 * 
 */

public class ModuleList extends ListDataView {

    private static final ModuleList INSTANCE = new ModuleList(Core.getModuleListTemplate(),
                    new Store(Modules.getList()));

    public static ModuleList get() {
        return INSTANCE;
    }

    protected ModuleList(String itemTemplate, Store store) {
        super(itemTemplate, store);
        // this.setWidth(300);
        // this.setDocked(Dock.LEFT);
        this.getElement().getStyle().setBorderColor("black");
        this.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
        this.getElement().getStyle().setBorderWidth(1, Unit.PX);
        this.addItemTapHandler(new DataViewItemTapHandler() {
            @Override
            public void onItemTap(DataView dataView, int index, Element element, BaseModel record, Object eventObject,
                            Object eOpts) {
                Controller.displayDemoFor(record.get(Module.MODULE_NAME));
            }
        });
    }
}
