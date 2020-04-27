package com.zero.game.component.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.zero.game.R
import com.zero.game.repository.model.FaceState
import com.zero.game.utils.AppConfig
import kotlinx.android.synthetic.main.activity_call.view.*
import kotlinx.android.synthetic.main.view_play.view.*

class PlayView : ConstraintLayout {

    private lateinit var playerView: ImageView
    private var lastFrameLayout: FrameLayout? = null
    private var imageViews = arrayListOf<ImageView>()
    var currentState = FaceState.NONE
    var playerCallback: PlayerCallback? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_play, this, true)
        playerView = ImageView(context)
        val lp = FrameLayout.LayoutParams(AppConfig.dp2px(96), AppConfig.dp2px(96))
        lp.gravity = Gravity.BOTTOM or Gravity.CENTER
        lp.bottomMargin = AppConfig.dp2px(24)
        playerView.layoutParams = lp
        setFaceState(FaceState.FRONT)
    }

    fun addPoint(faceState: FaceState) {
        val imageView = ImageView(context)
        val lp = FrameLayout.LayoutParams(AppConfig.dp2px(80), AppConfig.dp2px(80))
        lp.gravity = Gravity.TOP or Gravity.CENTER
        lp.topMargin = AppConfig.dp2px(-48)
        imageView.layoutParams = lp
        val ran = Math.random()
        if (ran > 0.5){
            imageView.setImageResource(R.mipmap.guo)
        }else{
            imageView.setImageResource(R.mipmap.guo2)
        }
        when (faceState) {
            FaceState.LEFT -> {
                play_left.addView(imageView)
            }
            FaceState.FRONT -> {
                play_center.addView(imageView)
            }
            FaceState.RIGHT -> {
                play_right.addView(imageView)
            }
            else -> {

            }
        }
        imageViews.add(imageView)
        startGuoAnimator(imageView, faceState)
    }

    private fun startGuoAnimator(imageView: ImageView, state: FaceState) {
        val valueAnimator = ValueAnimator.ofInt(AppConfig.dp2px(-96), play_view.height)
        valueAnimator.duration = 5000
        valueAnimator.addUpdateListener {
            val value = (it.animatedValue as Int)
            val lp = imageView.layoutParams as FrameLayout.LayoutParams
            lp.topMargin = value
            imageView.layoutParams = lp
            if (state == currentState &&
                ((imageView.bottom >= playerView.top && imageView.bottom <= playerView.bottom)
                        ||
                        (imageView.top >= playerView.bottom && imageView.top <= playerView.top))
            ) {
                valueAnimator.cancel()
            }
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(a: Animator?) {
            }

            override fun onAnimationEnd(a: Animator?) {
                remove(imageView, state)
            }

            override fun onAnimationCancel(a: Animator?) {
                playerCallback?.onGet()
                remove(imageView,state)
            }

            override fun onAnimationStart(a: Animator?) {
            }
        })
        valueAnimator.start()
    }


    private fun startFlyAwayAnimator(imageView: ImageView, state: FaceState) {
        val valueAnimator = ValueAnimator.ofInt(AppConfig.dp2px(96+24), play_view.height-AppConfig.dp2px(96+24))
        valueAnimator.duration = 5000
        valueAnimator.addUpdateListener {
            val value = (it.animatedValue as Int)
            val lp = imageView.layoutParams as FrameLayout.LayoutParams
            lp.bottomMargin = value
            imageView.layoutParams = lp
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(a: Animator?) {
            }

            override fun onAnimationEnd(a: Animator?) {
                remove(imageView, state)
                playerCallback?.onPointsFlyAway(state)
            }

            override fun onAnimationCancel(a: Animator?) {
            }

            override fun onAnimationStart(a: Animator?) {
            }
        })
        valueAnimator.start()
    }

    fun sendPoint(currentState: FaceState) {
        if (currentState == FaceState.NONE){
            return
        }
        val imageView = ImageView(context)
        val lp = FrameLayout.LayoutParams(AppConfig.dp2px(80), AppConfig.dp2px(80))
        lp.gravity = Gravity.BOTTOM or Gravity.CENTER
        lp.bottomMargin = AppConfig.dp2px(24+96)
        imageView.layoutParams = lp
        val ran = Math.random()
        if (ran > 0.5){
            imageView.setImageResource(R.mipmap.guo)
        }else{
            imageView.setImageResource(R.mipmap.guo2)
        }
        when (currentState) {
            FaceState.LEFT -> {
                play_left.addView(imageView)
            }
            FaceState.FRONT -> {
                play_center.addView(imageView)
            }
            FaceState.RIGHT -> {
                play_right.addView(imageView)
            }
            else -> {

            }
        }
        imageViews.add(imageView)
        startFlyAwayAnimator(imageView, currentState)
    }

    private fun remove(imageView: ImageView, state: FaceState) {
        val v = ValueAnimator.ofInt(AppConfig.dp2px(80), 0)
        v.duration = 500
        v.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                //remove(imageView, state)
                when (state) {
                    FaceState.LEFT -> {
                        play_left.removeView(imageView)
                    }
                    FaceState.RIGHT -> {
                        play_right.removeView(imageView)
                    }
                    FaceState.FRONT -> {
                        play_center.removeView(imageView)
                    }
                    else -> {

                    }
                }
                imageViews.remove(imageView)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
        v.addUpdateListener {
            val value = (it.animatedValue as Int)
            val lp = imageView.layoutParams as FrameLayout.LayoutParams
            lp.width = value
            lp.height = value
            imageView.layoutParams = lp
        }
        v.start()

    }

    fun startGame() {
        play_tip.visibility = View.GONE
        play_tip.text = context.getString(R.string.game_start)
        val valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        valueAnimator.duration = 3000
        valueAnimator.addUpdateListener {
            val value = (it.animatedValue as Float)
            play_tip.alpha = value
            play_tip.textSize = 70 * value
            if (play_tip.alpha < 0.1) {
                play_tip.visibility = View.VISIBLE
            }
            if (play_tip.alpha > 0.9) {
                play_tip.visibility = View.GONE
            }
        }
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.start()
    }

    fun setFaceState(faceState: FaceState) {
        currentState = faceState
        lastFrameLayout?.removeView(playerView)
        when (faceState) {
            FaceState.FRONT -> {
                lastFrameLayout = play_center
                playerView.setImageResource(R.mipmap.center)
                play_center.addView(playerView)
            }
            FaceState.LEFT -> {
                lastFrameLayout = play_left
                playerView.setImageResource(R.mipmap.right)
                play_left.addView(playerView)
            }
            FaceState.RIGHT -> {
                lastFrameLayout = play_right
                playerView.setImageResource(R.mipmap.left)
                play_right.addView(playerView)
            }
            else -> {
                lastFrameLayout = null
            }
        }
    }



    interface PlayerCallback {
        fun onGet()
        fun onPointsFlyAway(state: FaceState)
    }
}