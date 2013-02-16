package com.emitrom.touch4j.app.client.core;

import com.emitrom.touch4j.client.layout.FitLayout;
import com.emitrom.touch4j.client.ui.Panel;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;

/**
 * Base class for all demo panels
 */
public abstract class DemoPanel extends Panel {

    public DemoPanel() {
        this.getElement().getStyle().setBackgroundColor("black");
        this.setLayout(new FitLayout());
        this.getElement().getStyle().setBorderColor("black");
        this.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
        this.getElement().getStyle().setBorderWidth(2, Unit.PX);
    }
}
