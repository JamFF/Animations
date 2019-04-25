package com.ff.animations.splash.parallax;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.ff.animations.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;

/**
 * description: 自定义LayoutInflater，用于获取自定义的属性值
 * author: FF
 * time: 2019-04-24 09:13
 */
public class ParallaxLayoutInflater extends LayoutInflater {

    private static final String TAG = "ParallaxLayoutInflater";

    private ParallaxFragment mFragment;

    protected ParallaxLayoutInflater(LayoutInflater original, Context newContext, ParallaxFragment fragment) {
        super(original, newContext);
        mFragment = fragment;
        setFactory2(new ParallaxFactory(this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        Log.d(TAG, "cloneInContext: ");
        return new ParallaxLayoutInflater(this, newContext, mFragment);
    }

    /**
     * description: 自定义Factory2，解析自定义属性，保存到到View的Tag中
     * 使用静态内部类避免内存泄漏
     * author: FF
     * time: 2019-04-24 10:01
     */
    private static class ParallaxFactory implements Factory2 {

        private WeakReference<ParallaxLayoutInflater> inflaterWeakReference;

        // 系统控件前缀
        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view.",
                "android.webkit"
        };

        private ParallaxFactory(ParallaxLayoutInflater inflater) {
            inflaterWeakReference = new WeakReference<>(inflater);
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view = createMyView(name, context, attrs);
            if (view != null) {
                // 从AttributeSet取出自定义属性
                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ParallaxContainer);
                if (a != null) {
                    if (a.length() > 0) {
                        // 封装成Bean
                        ParallaxViewTag tag = new ParallaxViewTag();
                        tag.alphaIn = a.getFloat(R.styleable.ParallaxContainer_a_in, 0f);
                        tag.alphaOut = a.getFloat(R.styleable.ParallaxContainer_a_out, 0f);
                        tag.xIn = a.getFloat(R.styleable.ParallaxContainer_x_in, 0f);
                        tag.xOut = a.getFloat(R.styleable.ParallaxContainer_x_out, 0f);
                        tag.yIn = a.getFloat(R.styleable.ParallaxContainer_y_in, 0f);
                        tag.yOut = a.getFloat(R.styleable.ParallaxContainer_y_out, 0f);
                        // 设置到View的Tag中
                        view.setTag(R.id.parallax_view_tag, tag);
                    }
                    a.recycle();
                }
                if (inflaterWeakReference.get() != null) {
                    // 添加到集合中
                    inflaterWeakReference.get().mFragment.getParallaxViews().add(view);
                }
            }
            return view;
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return null;
        }

        /**
         * 通过控件名和属性信息，创建View
         *
         * @param name    控件名
         * @param context 上下文
         * @param attrs   属性信息
         * @return View
         */
        private View createMyView(String name, Context context, AttributeSet attrs) {
            View view = null;
            if (name.contains(".")) {
                // 包名+类名的控件，例如自定义的控件以及android.support.v7.widget.RecyclerView类似的系统控件
                // view = reflectView(name, null, attrs);// 通过系统API获取
                view = reflectView(name, null, context, attrs);// 通过反射获取
            } else {
                // 不含包名的系统控件，例如ImageView
                for (String prefix : sClassPrefix) {
                    // view = reflectView(name, prefix, attrs);// 通过系统API获取
                    view = reflectView(name, prefix, context, attrs);// 通过反射获取
                    if (view != null) {
                        break;
                    }
                }
            }
            return view;
        }

        /**
         * 调用LayoutInflater的API，创建View
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

        /**
         * 通过反射创建View
         *
         * @param name    控件名
         * @param prefix  如果name是包名+类名，传null
         *                如果name只有类名，传入包名
         * @param context 上下文
         * @param attrs   属性信息
         * @return View
         */
        private View reflectView(String name, String prefix, Context context, AttributeSet attrs) {
            try {
                Class<?> clazz;
                if (prefix == null) {
                    clazz = Class.forName(name);
                } else {
                    clazz = Class.forName(prefix + name);
                }
                Constructor constructor = clazz.getConstructor(Context.class, AttributeSet.class);
                return (View) constructor.newInstance(context, attrs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
