package com.ff.animations.splash.parallax;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * description: 使用ViewPager2的FragmentStateAdapter
 * author: FF
 * time: 2019-04-23 19:02
 */
public class ParallaxPagerAdapter2 extends FragmentStateAdapter {

    private List<ParallaxFragment> mFragments;

    /**
     * 在Activity中调用
     * 切记Fragment不要调用，FragmentManager会出问题
     */
    public ParallaxPagerAdapter2(@NonNull FragmentActivity fragmentActivity,
                                 List<ParallaxFragment> fragments) {
        // 比ViewPager的方便，根据传入的参数，内部获取FragmentManager
        super(fragmentActivity);
        mFragments = fragments;
    }

    /**
     * 在Fragment中调用
     */
    public ParallaxPagerAdapter2(@NonNull Fragment fragment,
                                 List<ParallaxFragment> fragments) {
        // 比ViewPager的方便，根据传入的参数，内部获取FragmentManager
        // 并且避免了使用Activity的FragmentManager，出现错误
        super(fragment);
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
