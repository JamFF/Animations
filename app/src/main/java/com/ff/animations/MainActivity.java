package com.ff.animations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ff.animations.animator.AnimatorFragment;
import com.ff.animations.splash.SplashActivity;
import com.ff.animations.splash.SplashFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnListItemClickListener {

    private FrameLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRoot = new FrameLayout(this);
        mRoot.setId(View.generateViewId());
        mRoot.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mRoot);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(mRoot.getId(), new MainFragment())
                    .commit();
        }
    }

    @Override
    public void onListItemClick(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new AnimatorFragment();
                break;
            case 1:
                startActivity(new Intent(this, SplashActivity.class));
                return;
            case 2:
                fragment = new SplashFragment();
                break;
            default:
                return;

        }
        getSupportFragmentManager().beginTransaction()
                .replace(mRoot.getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
}

