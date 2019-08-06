package com.yk.silence.customwallpaper.util

import android.content.Context

class ScreenUtil private constructor() {

    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {


        private var mStatusHeight = -1
        private val ORIGINAL_DENSITY = -1f  //原始屏幕密度比率

        /**
         * 获得状态栏的高度
         *
         * @return mStatusHeight
         */
        fun getStatusHeight(context: Context): Int {
            if (mStatusHeight != -1) {
                return mStatusHeight
            }
            try {
                val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    mStatusHeight = context.resources.getDimensionPixelSize(resourceId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return mStatusHeight
        }
    }


}
