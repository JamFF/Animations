package com.ff.animations.animator.core;

import android.animation.ObjectAnimator;
import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.Object;
import java.lang.String;

/**
 * description: 动画任务{@link ObjectAnimator}
 * author: FF
 * time: 2019-04-21 17:46
 */
public class MyObjectAnimator implements VSYNCManager.AnimationFrameCallback {

    private static final String TAG = "MyObjectAnimator";
    private float total;// 动画执行总帧数
    private WeakReference<View> target;// View属于比较重的对象，使用弱引用
    private float index = 0;
    private TimeInterpolator interpolator;// 插值器
    private MyFloatPropertyValuesHolder myFloatPropertyValuesHolder;// 动画处理类

    /**
     * 构造方法私有化，在每一个
     *
     * @param view         需要执行动画的View
     * @param propertyName 动画的属性名
     * @param values       动画随时间变化的一组值，这里模拟只设计float类型
     */
    private MyObjectAnimator(View view, String propertyName, float... values) {
        target = new WeakReference<>(view);
        myFloatPropertyValuesHolder = new MyFloatPropertyValuesHolder(propertyName, values);
    }

    /**
     * {@link ObjectAnimator#ofFloat(Object, String, float...)}
     *
     * @param view         View
     * @param propertyName 动画属性名
     * @param values       动画随时间变化的一组值，这里模拟只支持float类型
     *                     并且只支持两个参数，原因见{@link MyKeyframeSet#getValue(float)}
     * @return MyObjectAnimator
     */
    public static MyObjectAnimator ofFloat(View view, String propertyName, float... values) {
        return new MyObjectAnimator(view, propertyName, values);
    }

    public void setDuration(int duration) {
        // 动画执行总时常
        total = duration / 16f;// 16ms一帧
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void start() {
        myFloatPropertyValuesHolder.setupSetter(target);
        VSYNCManager.INSTANCE.add(this);
    }

    public void cancel() {
        VSYNCManager.INSTANCE.remove(this);
    }


    @Override
    public void doAnimationFrame() {// 16ms回调一次
        float fraction = (index++) / total;// 执行百分比，这里模拟计算的百分比
        if (interpolator != null) {
            // 通过插值器修改百分比，实现加速减速效果
            fraction = interpolator.getInterpolation(fraction);
        }
        if (index >= total) {
            index = 0;// 不断循环
        }
        // 根据百分比设置View
        myFloatPropertyValuesHolder.setAnimatedValue(target.get(), fraction);
    }
}
