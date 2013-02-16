package com.emitrom.touch4j.app.client.ui.tablet;

import com.emitrom.touch4j.client.fx.layout.card.Animation;
import com.emitrom.touch4j.client.fx.layout.card.AnimationType;
import com.emitrom.touch4j.client.laf.UI;
import com.emitrom.touch4j.client.ui.Button;
import com.emitrom.touch4j.client.utils.TouchIcons;

/**
 * Button to display the API reference for a given demo
 * 
 */
class ApiButton extends Button {

    private static final ApiButton INSTANCE = new ApiButton();

    public static ApiButton get() {
        return INSTANCE;
    }

    public ApiButton() {
        super();
        this.setHidden(true);
        this.setUi(UI.PLAIN);
        this.setIconMask(true);
        this.setIconCls(TouchIcons.COMPOSE);

        Animation showAnimation = new Animation(AnimationType.FADE_IN);
        showAnimation.setDuration(300);
        this.setShowAnimation(showAnimation);

        Animation hideAnimation = new Animation(AnimationType.FADE_OUT);
        hideAnimation.setDuration(300);
        this.setHideAnimation(hideAnimation);
    }

}
