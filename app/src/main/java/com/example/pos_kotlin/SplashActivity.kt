package com.example.pos_kotlin


import android.os.Bundle
import android.content.Intent
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val anim1 = AnimationUtils.loadAnimation(this, R.anim.anim_down)
        icon.setAnimation(anim1)

        val anim = ObjectAnimator.ofInt(progress, "progress", 0, 1000)
        anim.duration = 4000
        anim.interpolator = DecelerateInterpolator()
        anim.start()

        val handler = Handler()

        handler.postDelayed(Runnable {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}
