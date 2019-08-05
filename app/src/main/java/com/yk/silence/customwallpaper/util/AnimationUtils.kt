package com.yk.silence.customwallpaper.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View


object AnimationUtils {

    fun hideAddNote(mImgAdd: View, mImgDate: View, mImgEdit: View) {
        val animator = ObjectAnimator.ofFloat(mImgAdd,
                "alpha", 1.0f, 0.8f, 0.5f, 0.3f, 0.0f)
        val animator2 = ObjectAnimator.ofFloat(mImgDate,
                "alpha", 0.0f, 0.3f, 0.5f, 0.8f, 1.0f)
        val animator3 = ObjectAnimator.ofFloat(mImgEdit,
                "alpha", 0.0f, 0.3f, 0.5f, 0.8f, 1.0f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator, animator2,
                animator3)
        animatorSet.duration = 500
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                mImgAdd.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                mImgDate.visibility = View.VISIBLE
                mImgEdit.visibility = View.VISIBLE
            }

        })
        animatorSet.start()
    }

    fun showAddNote(mImgAdd: View, mImgDate: View, mImgEdit: View) {
        val animator = ObjectAnimator.ofFloat(mImgAdd,
                "alpha", 0.0f, 0.3f, 0.5f, 0.8f, 1.0f)
        val animator2 = ObjectAnimator.ofFloat(mImgDate,
                "alpha", 1.0f, 0.8f, 0.5f, 0.3f, 0.0f)
        val animator3 = ObjectAnimator.ofFloat(mImgEdit,
                "alpha", 1.0f, 0.8f, 0.5f, 0.3f, 0.0f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator, animator2,
                animator3)
        animatorSet.duration = 500
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                mImgDate.visibility = View.GONE
                mImgEdit.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                mImgAdd.visibility = View.VISIBLE
            }

        })
        animatorSet.start()
    }
}