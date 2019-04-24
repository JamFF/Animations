package com.ff.animations.splash.parallax;

import androidx.annotation.NonNull;

/**
 * description: 视差动画播放时参数的控制
 * author: FF
 * time: 2019-04-24 10:58
 */
public class ParallaxViewTag {
    protected int index;
    protected float xIn;
    protected float xOut;
    protected float yIn;
    protected float yOut;
    protected float alphaIn;
    protected float alphaOut;


    @NonNull
    @Override
    public String toString() {
        return "ParallaxViewTag{" +
                "index=" + index +
                ", xIn=" + xIn +
                ", xOut=" + xOut +
                ", yIn=" + yIn +
                ", yOut=" + yOut +
                ", alphaIn=" + alphaIn +
                ", alphaOut=" + alphaOut +
                '}';
    }
}
