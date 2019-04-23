package com.ff.animations.animator.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * description: 模拟
 * author: FF
 * time: 2019-04-22 10:46
 */
public enum VSYNCManager {

    INSTANCE;

    private Thread mThread;
    private volatile boolean running = false;// 标识线程是否停止
    private List<AnimationFrameCallback> list = new CopyOnWriteArrayList<>();

    VSYNCManager() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (running) {
                        Thread.sleep(16);// 16ms绘制一次
                        for (AnimationFrameCallback callback : list) {
                            callback.doAnimationFrame();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startThread() {
        if (!running && mThread != null) {
            running = true;
            mThread.start();
        }
    }

    public void stopThread() {
        clear();
        if (running && mThread != null) {
            running = false;
            // 调用interrupt()，因为线程有可能在wait()或sleep(), 提高停止线程的即时性
            mThread.interrupt();
        }
    }

    public void add(AnimationFrameCallback animationFrameCallback) {
        list.add(animationFrameCallback);
    }

    public void remove(AnimationFrameCallback animationFrameCallback) {
        list.remove(animationFrameCallback);
    }

    public void clear() {
        list.clear();
    }

    /**
     * description: android.animation.AnimationHandler.AnimationFrameCallback
     * author: FF
     * time: 2019-04-22 16:53
     */
    interface AnimationFrameCallback {
        /**
         * 16ms回调一次
         */
        void doAnimationFrame();
    }
}
