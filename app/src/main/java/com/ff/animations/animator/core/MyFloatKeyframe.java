package com.ff.animations.animator.core;

/**
 * description: float类型关键帧，里面变量保存着某一时刻的具体状态
 * {@link android.animation.Keyframe}
 * author: FF
 * time: 2019-04-21 18:27
 */
public class MyFloatKeyframe {

    private float mFraction;// 关键帧当前进度百分比
    private Class mValueType;// 关键帧类型
    private float mValue;// 关键帧当前值

    /**
     * 关键帧的构造方法，在初始化的时候就已经确认了
     *
     * @param fraction 关键帧的位置百分比
     * @param value    关键帧的值
     */
    public MyFloatKeyframe(float fraction, float value) {
        mFraction = fraction;
        mValue = value;
        mValueType = float.class;
    }

    public float getFraction() {
        return mFraction;
    }

    public void setFraction(float fraction) {
        mFraction = fraction;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float mValue) {
        this.mValue = mValue;
    }

    public Class getType() {
        return mValueType;
    }
}
