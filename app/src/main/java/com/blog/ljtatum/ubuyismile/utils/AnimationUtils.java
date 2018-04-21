package com.blog.ljtatum.ubuyismile.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.blog.ljtatum.ubuyismile.constants.Durations;

/**
 * Created by LJTat on 4/8/2018.
 */

public class AnimationUtils {

    /**
     * Method is used to retrieve blink animation object
     * @return An abstraction for an Animation that can be applied to Views, Surfaces, or other objects
     */
    public static Animation retrieveBlinkAnimation() {
        // Animation snippet
        final Animation animation = new AlphaAnimation(1.0F, 0.8F);
        animation.setDuration(Durations.ANIM_DURATION_MEDIUM_400);
        animation.setInterpolator(new LinearInterpolator());
        // Repeat animation infinitely
        animation.setRepeatCount(Animation.INFINITE);
        // Reverse animation at the end so the button will fade back in
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }
}
