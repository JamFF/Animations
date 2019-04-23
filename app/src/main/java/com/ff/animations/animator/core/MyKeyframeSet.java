package com.ff.animations.animator.core;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * description: 帧的管理类，android.animation.KeyframeSet
 * author: FF
 * time: 2019-04-21 18:26
 */
public class MyKeyframeSet {

    private static final String TAG = "MyKeyframeSet";

    private MyFloatKeyframe mFirstKeyframe;// 第一个关键帧
    private List<MyFloatKeyframe> mKeyframes;// 帧集合
    private TypeEvaluator mEvaluator;// 类型估值器，用来计算下一帧的值

    private MyKeyframeSet(MyFloatKeyframe... keyframes) {
        mKeyframes = Arrays.asList(keyframes);// 数组转为集合
        mFirstKeyframe = keyframes[0];
        mEvaluator = new FloatEvaluator();
    }

    /**
     * 将动画随时间变化的一组值，转换为关键帧集合
     *
     * @param values 动画随时间变化的一组值
     * @return 关键帧集合
     */
    public static MyKeyframeSet ofFloat(float[] values) {
        int numKeyframes = values.length;

        MyFloatKeyframe[] keyframes = new MyFloatKeyframe[numKeyframes];
        keyframes[0] = new MyFloatKeyframe(0, values[0]);// 关键帧集合的第一帧
        for (int i = 1; i < numKeyframes; ++i) {
            // 计算关键帧的位置百分比
            keyframes[i] = new MyFloatKeyframe((float) i / (numKeyframes - 1), values[i]);
        }
        return new MyKeyframeSet(keyframes);
    }

    /**
     * 通过即将执行的百分比，计算需要设置的值
     *
     * @param fraction 即将执行的百分比
     * @return 需要设置的值
     */
    public Object getValue(float fraction) {

        MyFloatKeyframe prevKeyframe = mFirstKeyframe;// 上一帧
        for (int i = 1; i < mKeyframes.size(); ++i) {
            MyFloatKeyframe nextKeyframe = mKeyframes.get(i);// 下一帧
            if (fraction < nextKeyframe.getFraction()) {// 通过百分比，找到上下帧
                // 通过估值器计算出下一帧的值
                Log.d(TAG, "getValue: fraction = " + fraction +
                        ", prevKeyframe = " + prevKeyframe.getValue() +
                        ", nextKeyframe = " + nextKeyframe.getValue());
                // 这里百分比是整体的，所以这里不支持大于两个关键帧，计算value会出问题
                Object value = mEvaluator.evaluate(fraction, prevKeyframe.getValue(), nextKeyframe.getValue());
                Log.d(TAG, "getValue: " + value);
                return value;
            }
            // 百分比大于下一帧，则更新上一帧，重新找下一帧
            prevKeyframe = nextKeyframe;
        }
        Log.e(TAG, "getValue: null");
        return null;// 如果只有一个关键帧，跳出for循环，直接返回null
    }
}
