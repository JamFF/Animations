package com.ff.animations.animator;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ff.animations.R;
import com.ff.animations.animator.core.LinearInterpolator;
import com.ff.animations.animator.core.MyObjectAnimator;
import com.ff.animations.animator.core.VSYNCManager;

/**
 * description: 手写动画框架
 * author: FF
 * time: 2019-04-21 10:39
 */
public class AnimatorFragment extends Fragment implements View.OnClickListener {

    private MyObjectAnimator mAnimator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LinearLayout ll = new LinearLayout(getContext());
        // ll的父容器是MainActivity中的FrameLayout
        ll.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setGravity(Gravity.CENTER);

        TextView vt = new TextView(getContext());
        vt.setGravity(Gravity.CENTER);
        vt.setBackgroundResource(android.R.color.holo_red_dark);
        vt.setText(getText(R.string.scale));
        vt.setId(R.id.tv);
        ll.addView(vt, new LinearLayout.LayoutParams(100, 100));

        vt.setOnClickListener(this);
        VSYNCManager.INSTANCE.startThread();
        return ll;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv) {
            mAnimator = MyObjectAnimator.ofFloat(v, "scaleX", 1f, 2f);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.setDuration(3000);
            mAnimator.start();
        }
    }

    @Override
    public void onDestroyView() {
        mAnimator.cancel();
        VSYNCManager.INSTANCE.stopThread();
        super.onDestroyView();
    }
}
