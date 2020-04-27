package mobi.accessible.show.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import mobi.accessible.show.R;

public class SpreadView extends FrameLayout {

    // The delay to run the next animation if not stopped
    private static final int ANIM_DELAY = 1050;

    private static final int MSG_START = 0;
    private static final int MSG_FORCE_STOP = 1;

    private Context mContext;
    private View mLayer1;
    private View mLayer2;

    private AnimationSet mAnimLayer1;
    private AnimationSet mAnimLayer2;

    private boolean mIsRunning;
    private PortraitAnimationHandler mHandler;
    private PortraitAnimationRunnable mRunnable;

    public SpreadView(Context context) {
        this(context, null, 0);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void initView() {
        setVisibility(GONE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLayer1 = new ImageView(mContext);
        mLayer1.setBackgroundResource(R.drawable.bg_seat_grid_item);
        mLayer2 = new ImageView(mContext);
        mLayer2.setBackgroundResource(R.drawable.bg_seat_grid_item);
        addView(mLayer1, layoutParams);
        addView(mLayer2, layoutParams);
    }

    private void init() {
        mHandler = new PortraitAnimationHandler(mContext.getMainLooper());
        mRunnable = new PortraitAnimationRunnable();
        initView();
        initAnimation1();
        initAnimation2();
    }

    private void initAnimation1() {
        mAnimLayer1 = new AnimationSet(true);
        AlphaAnimation alpha1 = new AlphaAnimation(0, 1);
        alpha1.setDuration(200);
        alpha1.setInterpolator(new LinearInterpolator());

        AlphaAnimation alpha2 = new AlphaAnimation(1, 0.2f);
        alpha2.setDuration(700);
        alpha2.setStartOffset(200);
        alpha2.setInterpolator(new AccelerateDecelerateInterpolator());

        AlphaAnimation alpha3 = new AlphaAnimation(0.2f, 0);
        alpha3.setDuration(100);
        alpha3.setStartOffset(900);
        alpha3.setInterpolator(new LinearInterpolator());

        mAnimLayer1.setDuration(1000);
        mAnimLayer1.setFillAfter(true);
        mAnimLayer1.addAnimation(alpha1);
        mAnimLayer1.addAnimation(alpha2);
        mAnimLayer1.addAnimation(alpha3);
    }

    private void initAnimation2() {
        mAnimLayer2 = new AnimationSet(true);
        ScaleAnimation scale = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);

        AlphaAnimation alpha1 = new AlphaAnimation(0, 1);
        alpha1.setDuration(125);

        AlphaAnimation alpha2 = new AlphaAnimation(1, 0);
        alpha2.setDuration(875);
        alpha2.setStartOffset(125);

        mAnimLayer2.setFillAfter(true);
        mAnimLayer2.addAnimation(scale);
        mAnimLayer2.addAnimation(alpha1);
        mAnimLayer2.addAnimation(alpha2);
    }

    public void startAnimation() {
        Message message = new Message();
        message.what = MSG_START;
        mHandler.sendMessage(message);
    }

    private class PortraitAnimationHandler extends Handler {
        private PortraitAnimationHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_START) {
                if (!mIsRunning) {
                    mIsRunning = true;
                    mHandler.post(mRunnable);
                    mHandler.postDelayed(this::stop, ANIM_DELAY);
                }
            } else if (msg.what == MSG_FORCE_STOP) {
                forceStop();
            }
        }

        private void stop() {
            mIsRunning = false;
            mLayer1.clearAnimation();
            mLayer2.clearAnimation();
            setVisibility(GONE);
        }

        private void forceStop() {
            stop();
            mHandler.removeCallbacks(mRunnable);
        }
    }

    private class PortraitAnimationRunnable implements Runnable {
        @Override
        public void run() {
            setVisibility(VISIBLE);
            mLayer1.startAnimation(mAnimLayer1);
            mLayer2.startAnimation(mAnimLayer2);
        }
    }

}
