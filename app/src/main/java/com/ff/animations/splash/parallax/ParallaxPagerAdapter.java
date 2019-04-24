package com.ff.animations.splash.parallax;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * description: 使用ViewPager的FragmentPagerAdapter
 * author: FF
 * time: 2019-04-23 19:02
 */
public class ParallaxPagerAdapter extends FragmentPagerAdapter {

    private List<ParallaxFragment> mFragments;

    /**
     * 在Activity中调用，重点区别FragmentManager
     * 切记Fragment不要调用，FragmentManager会出问题
     */
    public ParallaxPagerAdapter(@NonNull FragmentActivity fragmentActivity,
                                List<ParallaxFragment> fragments) {
        super(fragmentActivity.getSupportFragmentManager());
        mFragments = fragments;
    }

    /**
     * 在Fragment中调用，重点区别FragmentManager
     */
    public ParallaxPagerAdapter(@NonNull Fragment fragment,
                                List<ParallaxFragment> fragments) {
        // Fragment嵌套要使用getChildFragmentManager
        super(fragment.getChildFragmentManager());
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
