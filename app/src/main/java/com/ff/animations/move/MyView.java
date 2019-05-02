package com.ff.animations.move;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * description: 自定义一个控件，判断手指是否触摸在这个范围之内，并作出相应的提示，
 * 当手指触摸到了范围之内，让控件跟随手指移动而移动。
 * author: FF
 * time: 2019-05-02 10:50
 */
public class MyView extends AppCompatTextView {

    private float mDownRawX;
    private float mDownRawY;

    private float mDownTranslationX;
    private float mDownTranslationY;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Toast.makeText(getContext(), "触摸范围内", Toast.LENGTH_SHORT).show();

                mDownTranslationX = getTranslationX();
                mDownTranslationY = getTranslationY();
                mDownRawX = event.getRawX();
                mDownRawY = event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:

                setTranslationX(mDownTranslationX + event.getRawX() - mDownRawX);
                setTranslationY(mDownTranslationY + event.getRawY() - mDownRawY);

                break;
        }
        return true;
    }
}
