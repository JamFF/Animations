package com.ff.animations.splash.parallax;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.ff.animations.R;

import java.lang.ref.WeakReference;

/**
 * description: 自定义LayoutInflater，用于获取自定义的属性值
 * author: FF
 * time: 2019-04-24 09:13
 */
public class ParallaxLayoutInflater extends LayoutInflater {

    private static final String TAG = "ParallaxLayoutInflater";

    private ParallaxFragment mFragment;

    protected ParallaxLayoutInflater(Context context) {
        super(context);
        Log.d(TAG, "ParallaxLayoutInflater: 1");
    }

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
        Log.d(TAG, "ParallaxLayoutInflater: 2");
    }

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext, ParallaxFragment fragment) {
        this(original, newContext);
        Log.d(TAG, "ParallaxLayoutInflater: 3");
        mFragment = fragment;
        setFactory2(new ParallaxFactory(this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        Log.d(TAG, "cloneInContext: ");
        return new ParallaxLayoutInflater(this, newContext, mFragment);
    }

    private static class ParallaxFactory implements Factory2 {

        private WeakReference<ParallaxLayoutInflater> inflaterWeakReference;

        // 系统控件前缀
        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view."
        };

        int[] attrIds = {
                R.attr.a_in,
                R.attr.a_out,
                R.attr.x_in,
                R.attr.x_out,
                R.attr.y_in,
                R.attr.y_out};

        public ParallaxFactory(ParallaxLayoutInflater inflater) {
            inflaterWeakReference = new WeakReference<>(inflater);
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            Log.d(TAG, "onCreateView: ");
            View view = createMyView(name, attrs);
            if (view != null) {
                TypedArray a = context.obtainStyledAttributes(attrs, attrIds);
                if (a != null) {
                    if (a.length() > 0) {
                        ParallaxViewTag tag = new ParallaxViewTag();
                        tag.alphaIn = a.getFloat(0, 0f);
                        tag.alphaOut = a.getFloat(1, 0f);
                        tag.xIn = a.getFloat(2, 0f);
                        tag.xOut = a.getFloat(3, 0f);
                        tag.yIn = a.getFloat(4, 0f);
                        tag.yOut = a.getFloat(5, 0f);
                        view.setTag(R.id.parallax_view_tag, tag);
                    }
                    a.recycle();
                }
                if (inflaterWeakReference.get() != null) {
                    inflaterWeakReference.get().mFragment.getParallaxViews().add(view);
                }
            }
            Log.i(TAG, "onCreateView:view " + view);
            return view;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            Log.d(TAG, "onCreateView: ");
            return null;
        }

        /**
         * 通过控件名和属性信息，获取View
         *
         * @param name  控件名
         * @param attrs 属性信息
         * @return View
         */
        private View createMyView(String name, AttributeSet attrs) {
            if (name.contains(".")) {
                // 自定义的控件，或者android.support.v7.widget.RecyclerView的完整类名
                return reflectView(name, null, attrs);
            } else {
                // 系统控件
                for (String prefix : sClassPrefix) {
                    View view = reflectView(name, prefix, attrs);
                    // 获取系统控件的自定义属性
                    if (view != null) {
                        return view;
                    }
                }
            }
            return null;
        }

        /**
         * 反射创建View
         *
         * @param name   控件名
         * @param prefix 如果name是包名+类名，传null
         *               如果name只有类名，传入包名
         * @param attrs  属性信息
         * @return View
         */
        private View reflectView(String name, String prefix, AttributeSet attrs) {

            try {
                if (inflaterWeakReference.get() == null) {
                    return null;
                }
                // 通过统的inflater创建视图，读取系统的属性
                return inflaterWeakReference.get().createView(name, prefix, attrs);// 内部使用反射
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
