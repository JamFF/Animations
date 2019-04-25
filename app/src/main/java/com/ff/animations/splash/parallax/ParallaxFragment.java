package com.ff.animations.splash.parallax;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 加载到ViewPager中到Fragment
 * author: FF
 * time: 2019-04-23 17:57
 */
public class ParallaxFragment extends Fragment {

    private static final String LAYOUT_ID = "layoutId";

    // 此Fragment上所有的需要实现视差动画的视图
    private List<View> parallaxViews = new ArrayList<>();

    public static ParallaxFragment newInstance(@LayoutRes int layoutId) {
        ParallaxFragment fragment = new ParallaxFragment();
        Bundle args = new Bundle();
        // Fragment中需要加载的布局文件id
        args.putInt(LAYOUT_ID, layoutId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        @LayoutRes int layoutId = 0;
        if (getArguments() != null) {
            layoutId = getArguments().getInt(LAYOUT_ID);
        }
        // return inflater.inflate(layoutId, container, false);
        // 使用自定义的inflater进行解析
        return new ParallaxLayoutInflater(inflater, getContext(), this)
                .inflate(layoutId, container, false);
    }

    public List<View> getParallaxViews() {
        return parallaxViews;
    }
}
