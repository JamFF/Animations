package com.ff.animations.animator.core;

import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * description: 处理类{@link android.animation.PropertyValuesHolder}
 * author: FF
 * time: 2019-04-21 18:07
 */
public class MyFloatPropertyValuesHolder {

    private static final String TAG = "PropertyValuesHolder";

    String mPropertyName;// 属性名例如：scaleX
    // float
    Class mValueType;
    MyKeyframeSet mKeyframes;// 关键帧管理类
    Method mSetter = null;

    /**
     * 构造方法
     *
     * @param propertyName
     * @param values       动画随时间变化的一组值
     */
    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        this.mPropertyName = propertyName;
        mValueType = float.class;
        mKeyframes = MyKeyframeSet.ofFloat(values);// 交给MyKeyframeSet转换为关键帧
    }

    public void setupSetter(WeakReference<View> target) {
        // 例如mPropertyName是scaleX
        char firstLetter = Character.toUpperCase(mPropertyName.charAt(0));// S
        String theRest = mPropertyName.substring(1);// caleX
        String methodName = "set" + firstLetter + theRest;// setScaleX
        try {
            mSetter = View.class.getMethod(methodName, float.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据百分比设置View
     *
     * @param target   View
     * @param fraction 即将执行的百分比
     */
    void setAnimatedValue(View target, float fraction) {
        Object value = mKeyframes.getValue(fraction);
        if (mSetter != null && value != null) {
            try {
                mSetter.invoke(target, value);// target.setScaleX(value);
            } catch (InvocationTargetException e) {
                Log.e(TAG, e.toString());
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.toString());
            }
        }
    }
}
