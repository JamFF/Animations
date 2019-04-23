package com.ff.animations.animator.core;

/**
 * description: 线性插值器{@link android.view.animation.LinearInterpolator}
 * author: FF
 * time: 2019-04-22 15:27
 */
public class LinearInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return input;
    }
}
