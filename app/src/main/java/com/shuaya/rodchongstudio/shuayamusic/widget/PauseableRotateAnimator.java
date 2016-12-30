package com.shuaya.rodchongstudio.shuayamusic.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by RodChong on 2016/12/30.
 */

public class PauseableRotateAnimator {

    private ObjectAnimator animator;
    private AnimationLinstener listener;
    private View target;

    public PauseableRotateAnimator(View target, long duration) {
        this.animator = ObjectAnimator.ofFloat(target, "Rotation", 0f, 360f);
        this.animator.setDuration(duration);
        this.animator.setRepeatCount(ObjectAnimator.INFINITE);
        this.listener = new AnimationLinstener();
        this.animator.addUpdateListener(listener);
    }

    public void Pause() {
        listener.Pause();
    }

    public void Start() {
        if (listener.is_pause)
            listener.Start();
        else {
            animator.start();
        }
    }

    public void Stop() {
        animator.end();
    }

    public class AnimationLinstener implements ValueAnimator.AnimatorUpdateListener {

        private boolean is_pause = false;

        private boolean paused = false;

        private float fraction = 0f;

        private long time = 0L;

        private CountDownTimer timer = new CountDownTimer(ValueAnimator.getFrameDelay(), ValueAnimator.getFrameDelay()) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                animator.setCurrentPlayTime(time);
            }

        };

        public void Pause() {
            is_pause = true;
        }

        public void Start() {
            is_pause = false;
            paused = false;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            if (is_pause) {
                if (!paused) { //使其中的内容只执行一次
                    time = animation.getCurrentPlayTime();
                    fraction = animation.getAnimatedFraction();
                    animation.setInterpolator(new Interpolator() {
                        @Override
                        public float getInterpolation(float input) {
                            return fraction;
                        }
                    });
                    paused = true;
                }
                timer.start();
            } else {
                animation.setInterpolator(new LinearInterpolator());
            }
        }

    }
}
