package com.ff.animations.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ff.animations.R;
import com.ff.animations.splash.parallax.ParallaxContainer;

/**
 * description: 在Fragment中使用视差动画
 * author: FF
 * time: 2019-04-21 10:39
 */
public class SplashFragment extends Fragment {

    private ParallaxContainer mContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        mContainer = view.findViewById(R.id.parallax_container);
        mContainer.setUp(this,
                R.layout.view_intro_1,
                R.layout.view_intro_2,
                R.layout.view_intro_3,
                R.layout.view_intro_4,
                R.layout.view_intro_5,
                R.layout.view_login);
        mContainer.setIv_man(view.findViewById(R.id.iv_man));
        mContainer.addListener();
        return view;
    }

    @Override
    public void onDestroyView() {
        mContainer.removeListener();
        super.onDestroyView();
    }
}
