package com.ff.animations.animator.core;

/**
 * description: 估值器{@link android.animation.TypeEvaluator}
 * author: FF
 * time: 2019-04-22 18:09
 */
public interface TypeEvaluator<T> {
    public T evaluate(float fraction, T startValue, T endValue);
}
