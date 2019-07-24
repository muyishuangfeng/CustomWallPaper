package com.yk.silence.customwallpaper.ui.behaivor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yk.silence.customwallpaper.R;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class ScrollAwareBehavior extends FloatingActionButton.Behavior {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;

    public ScrollAwareBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        } else if (dyConsumed < 0) {
            animateIn(child);
        }
    }

    private void animateOut(final FloatingActionButton button) {
        ViewCompat.animate(button).scaleX(0.0F).scaleY(0.0F).alpha(0.0F)
                .setInterpolator(INTERPOLATOR).withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        ScrollAwareBehavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationCancel(View view) {
                        ScrollAwareBehavior.this.mIsAnimatingOut = false;
                    }

                    public void onAnimationEnd(View view) {
                        ScrollAwareBehavior.this.mIsAnimatingOut = false;
                    }
                }).start();
    }

    @SuppressLint("RestrictedApi")
    private void animateIn(FloatingActionButton button) {
        button.setVisibility(View.VISIBLE);
        ViewCompat.animate(button).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .start();
    }
}
