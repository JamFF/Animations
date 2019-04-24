package com.ff.animations.splash;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.ff.animations.R;
import com.ff.animations.splash.parallax.ParallaxContainer;

/**
 * description: 在Activity使用视差动画
 * author: FF
 * time: 2019-04-23 20:17
 */
public class SplashActivity extends FragmentActivity {

    private ParallaxContainer mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = findViewById(R.id.parallax_container);
        mContainer.setUp(this,
                R.layout.view_intro_1,
                R.layout.view_intro_2,
                R.layout.view_intro_3,
                R.layout.view_intro_4,
                R.layout.view_intro_5,
                R.layout.view_login);
        mContainer.setIv_man(findViewById(R.id.iv_man));
        mContainer.addListener();
    }

    @Override
    protected void onDestroy() {
        mContainer.removeListener();
        super.onDestroy();
    }
}
