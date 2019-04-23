package com.ff.animations.animator.core;

/**
 * description: 类型估值器，用来计算下一帧的值{@link android.animation.FloatEvaluator}
 * author: FF
 * time: 2019-04-22 18:12
 */
public class FloatEvaluator implements TypeEvaluator<Number> {

    /**
     * 计算下一帧需要设置的值
     *
     * @param fraction   即将执行的百分比
     * @param startValue 上一关键帧的值
     * @param endValue   下一关键帧的值
     * @return 下一帧需要设置的值
     */
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
}
