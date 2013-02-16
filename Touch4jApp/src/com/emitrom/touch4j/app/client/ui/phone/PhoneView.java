package com.emitrom.touch4j.app.client.ui.phone;

import com.emitrom.touch4j.client.core.handlers.navigation.NavigationViewEventHandler;
import com.emitrom.touch4j.client.fx.layout.card.Animation;
import com.emitrom.touch4j.client.fx.layout.card.AnimationType;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.ui.NavigationView;
import com.emitrom.touch4j.client.utils.TouchIcons;
import com.google.gwt.user.client.Timer;

/**
 * Implements the main view for the phone
 * 
 * 
 */
public class PhoneView extends NavigationView {

    private static PhoneView INSTANCE = new PhoneView();
    private Button apiButton;

    public static PhoneView get() {
        return INSTANCE;
    }

    private PhoneView() {
        this.push(PhoneStarPage.get());
        apiButton = this.addNavButton("");
        apiButton.setIconMask(true);
        apiButton.setIconCls(TouchIcons.COMPOSE);
        new Timer() {
            @Override
            public void run() {
                apiButton.setHidden(true);
            }
        }.schedule(100);

        Animation showAnimation = new Animation(AnimationType.FADE_IN);
        showAnimation.setDuration(300);
        apiButton.setShowAnimation(showAnimation);

        Animation hideAnimation = new Animation(AnimationType.FADE_OUT);
        hideAnimation.setDuration(300);
        apiButton.setHideAnimation(hideAnimation);
        this.addBackHandler(new NavigationViewEventHandler() {
            public void onEvent(NavigationView view, Object eOpts) {
                apiButton.hide();
            }
        });

    }

    public Button getApiButton() {
        return apiButton;
    }
}
