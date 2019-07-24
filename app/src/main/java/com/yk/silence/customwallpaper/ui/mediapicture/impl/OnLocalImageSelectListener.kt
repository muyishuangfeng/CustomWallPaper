package com.yk.silence.customwallpaper.ui.mediapicture.impl

import android.view.View

interface OnLocalImageSelectListener {

    fun onImageSelect(view: View, position: Int, images: List<String>)
}