package com.yk.silence.customwallpaper.ui.dialog

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.ViewGroup
import android.view.Window

import com.google.android.material.bottomsheet.BottomSheetDialog

import java.util.Objects
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes

class CustomBottomSheetDialog : BottomSheetDialog {

    private var mContext: Context? = null


    constructor(context: Context?) : super(context!!) {
        this.mContext = context
    }

    constructor(context: Context?, @StyleRes theme: Int) : super(context!!, theme) {
        this.mContext = context
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screenHeight = getScreenHeight((mContext as Activity?)!!)
        val statusBarHeight = getStatusBarHeight(mContext!!)
        val dialogHeight = screenHeight - statusBarHeight
        Objects.requireNonNull<Window>(window)
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, if (dialogHeight == 0)
                    ViewGroup.LayoutParams.MATCH_PARENT
                else
                    dialogHeight)
    }

    private fun getScreenHeight(activity: Activity): Int {
        val displaymetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displaymetrics)
        return displaymetrics.heightPixels
    }

    private fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val res = context.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return event.keyCode == KeyEvent.KEYCODE_BACK || super.dispatchKeyEvent(event)
    }
}
