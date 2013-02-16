package com.emitrom.touch4j.app.client.ui.tablet;

import com.emitrom.touch4j.app.client.core.Core;
import com.emitrom.touch4j.app.client.ui.common.ModuleList;
import com.emitrom.touch4j.client.core.config.Dock;
import com.emitrom.touch4j.client.fx.layout.card.Animation;
import com.emitrom.touch4j.client.fx.layout.card.AnimationType;
import com.emitrom.touch4j.client.fx.layout.card.Direction;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.layout.CardLayout;
import com.emitrom.touch4j.client.layout.FitLayout;
import com.emitrom.touch4j.client.ui.Container;
import com.emitrom.touch4j.client.ui.Image;
import com.emitrom.touch4j.client.ui.Panel;
import com.emitrom.touch4j.client.ui.Spacer;
import com.emitrom.touch4j.client.ui.ToolBar;
import com.google.gwt.user.client.Timer;

public class TabletView extends Panel {

    private static final TabletView INSTANCE = new TabletView();

    public static TabletView get() {
        return INSTANCE;
    }

    private Panel showCasePanel;

    private TabletView() {

        this.setLayout(new FitLayout());

        ToolBar toolBar = new ToolBar();
        toolBar.setDocked(Dock.TOP);
        toolBar.setUi(UI.DARK);
        toolBar.setTitle(Core.getAppTitle());
        toolBar.add(new Spacer());
        toolBar.add(ApiButton.get());
        new Timer() {
            @Override
            public void run() {
                ApiButton.get().setHidden(true);
            }
        }.schedule(100);
        this.add(toolBar);

        CardLayout layout = new CardLayout();
        layout.setAnimation(new Animation(AnimationType.SLIDE, Direction.LEFT, 200));

        showCasePanel = new Panel();
        showCasePanel.setLayout(layout);

        Image image = new Image();
        image.setSrc(Core.LOGO);
        image.setWidth(200);
        image.setHeight(200);
        image.setCentered(true);

        Panel imageHolder = new Panel();
        imageHolder.getElement().getStyle().setBackgroundColor("black");
        imageHolder.setHtml(Core.getWelcomeText());
        imageHolder.add(image);

        // showCasePanel.add(imageHolder);
        showCasePanel.add(ModuleList.get());

        this.add(showCasePanel);

    }

    public void addDemo(Container container) {
        showCasePanel.setActiveItem(container);
    }

    public ApiButton getApiButton() {
        return ApiButton.get();
    }
}
