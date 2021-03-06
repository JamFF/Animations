package com.ff.animations.splash.parallax;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.ff.animations.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 自定义视差控件
 * author: FF
 * time: 2019-04-23 18:40
 */
public class ParallaxContainer extends FrameLayout {

    private static final String TAG = "ParallaxContainer";

    private static final boolean IS_VIEWPAGER_2 = true;// 使用ViewPager2

    private List<ParallaxFragment> mFragments = new ArrayList<>();

    private ViewPager vp;
    private ViewPager2 vp2;

    private ParallaxPagerAdapter mAdapter;
    private ParallaxPagerAdapter2 mAdapter2;

    private View iv_man;

    private OnPageChangeListener mCallback;

    public ParallaxContainer(@NonNull Context context) {
        super(context);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIv_man(View iv_man) {
        this.iv_man = iv_man;
    }

    /**
     * 给自定义控件增加布局
     *
     * @param childIds ViewPager中需要加载的layout
     */
    public void setUp(FragmentActivity activity, int... childIds) {
        if (IS_VIEWPAGER_2) {
            vp2 = new ViewPager2(getContext());
            vp2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            for (int childId : childIds) {
                // 根据布局文件数组，初始化所有的fragment
                mFragments.add(ParallaxFragment.newInstance(childId));
            }
            mAdapter2 = new ParallaxPagerAdapter2(activity, mFragments);
            vp2.setAdapter(mAdapter2);
            addView(vp2);
        } else {
            vp = new ViewPager(getContext());
            vp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            for (int childId : childIds) {
                mFragments.add(ParallaxFragment.newInstance(childId));
            }
            mAdapter = new ParallaxPagerAdapter(activity, mFragments);
            vp.setAdapter(mAdapter);
            addView(vp);
        }
    }

    /**
     * 给自定义控件增加布局
     *
     * @param childIds ViewPager中需要加载的layout
     */
    public void setUp(Fragment fragment, int... childIds) {
        if (IS_VIEWPAGER_2) {
            vp2 = new ViewPager2(getContext());
            vp2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            for (int childId : childIds) {
                mFragments.add(ParallaxFragment.newInstance(childId));
            }
            mAdapter2 = new ParallaxPagerAdapter2(fragment, mFragments);
            vp2.setAdapter(mAdapter2);
            addView(vp2);
        } else {
            vp = new ViewPager(getContext());
            vp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            for (int childId : childIds) {
                mFragments.add(ParallaxFragment.newInstance(childId));
            }
            mAdapter = new ParallaxPagerAdapter(fragment, mFragments);
            vp.setAdapter(mAdapter);
            addView(vp);
        }
    }

    /**
     * 注册滑动监听
     */
    public void addListener() {
        if (mCallback == null) {
            mCallback = new OnPageChangeListener(this);
        }
        if (IS_VIEWPAGER_2 && vp2 != null) {
            vp2.registerOnPageChangeCallback(mCallback);
        } else if (!IS_VIEWPAGER_2 && vp != null) {
            vp.addOnPageChangeListener(mCallback);
        }
    }

    /**
     * 反注册滑动监听
     */
    public void removeListener() {
        if (mCallback == null) {
            return;
        }
        if (IS_VIEWPAGER_2 && vp2 != null) {
            vp2.unregisterOnPageChangeCallback(mCallback);
        } else if (!IS_VIEWPAGER_2 && vp != null) {
            vp.removeOnPageChangeListener(mCallback);
        }
    }

    /**
     * description: ViewPager和ViewPager2公用的滑动监听
     * 使用静态内部类避免内存泄漏
     * author: FF
     * time: 2019-04-24 13:56
     */
    private static final class OnPageChangeListener extends ViewPager2.OnPageChangeCallback
            implements ViewPager.OnPageChangeListener {

        private WeakReference<ParallaxContainer> containerWeakReference;

        public OnPageChangeListener(ParallaxContainer container) {
            containerWeakReference = new WeakReference<>(container);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // position: 当前屏幕左侧的位置，不论左划右划
            // positionOffset: 从右往左，0到1变化，从左往右，1到0变化
            // positionOffsetPixels: 从右往左，从0到屏幕的宽度数值，从左往右，从屏幕的宽度数值到0

            ParallaxContainer container = containerWeakReference.get();
            if (container == null || position < 0) {
                return;
            }

            ParallaxFragment leftFragment = null;// 左侧的Fragment
            if (position > 0) {
                leftFragment = container.mFragments.get(position - 1);
            }
            ParallaxFragment rightFragment = container.mFragments.get(position);// 右侧的Fragment

            int containerWidth = container.getWidth();

            if (leftFragment != null) {
                // 获取Fragment上所有的视图，实现动画效果
                List<View> leftViews = leftFragment.getParallaxViews();
                if (leftViews != null) {
                    ParallaxViewTag tag;
                    for (View view : leftViews) {
                        // 获取标签，从标签上获取所有的动画参数
                        tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                        if (tag == null) {
                            continue;
                        }
                        // 动画
                        view.setTranslationX((containerWidth - positionOffsetPixels) * tag.xIn);
                        view.setTranslationY(0 - (containerWidth - positionOffsetPixels) * tag.yIn);
                        view.setAlpha(1.0f - (containerWidth - positionOffsetPixels) * tag.alphaIn / containerWidth);
                    }
                }
            }
            if (rightFragment != null) {
                List<View> rightViews = rightFragment.getParallaxViews();
                if (rightViews != null) {
                    ParallaxViewTag tag;
                    for (View view : rightViews) {
                        tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                        if (tag == null) {
                            continue;
                        }
                        view.setTranslationX(0 - positionOffsetPixels * tag.xOut);
                        view.setTranslationY(0 - positionOffsetPixels * tag.yOut);
                        view.setAlpha(1.0f - positionOffsetPixels * tag.alphaOut / containerWidth);
                    }
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            ParallaxContainer container = containerWeakReference.get();
            if (container == null || container.iv_man == null) {
                return;
            }
            if (IS_VIEWPAGER_2 && container.mAdapter2 != null) {
                if (position == container.mAdapter2.getItemCount() - 1) {
                    container.iv_man.setVisibility(GONE);
                } else {
                    container.iv_man.setVisibility(VISIBLE);
                }
            } else if (!IS_VIEWPAGER_2 && container.mAdapter != null) {
                if (position == container.mAdapter.getCount() - 1) {
                    container.iv_man.setVisibility(GONE);
                } else {
                    container.iv_man.setVisibility(VISIBLE);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            ParallaxContainer container = containerWeakReference.get();
            if (container == null || container.iv_man == null) {
                return;
            }
            AnimationDrawable animation = (AnimationDrawable) container.iv_man.getBackground();
            if (animation == null) {
                return;
            }
            switch (state) {
                case ViewPager2.SCROLL_STATE_IDLE:
                    // 与ViewPager.SCROLL_STATE_IDLE值一样
                    animation.stop();
                    break;
                case ViewPager2.SCROLL_STATE_DRAGGING:
                    // 与ViewPager.SCROLL_STATE_DRAGGING值一样
                    animation.start();
                    break;
                case ViewPager2.SCROLL_STATE_SETTLING:
                    // 与ViewPager.SCROLL_STATE_SETTLING:值一样
                    animation.stop();
                    break;
            }
        }
    }
}
