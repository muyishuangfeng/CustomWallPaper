package com.yk.silence.customwallpaper.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView


class DensityUtils private constructor() {

    init {
        // 不能被实例化
        UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        var isFullScreen = false
            private set
        private val dm: DisplayMetrics? = null

        /**
         * dp转px
         *
         */
        fun dp2px(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dpVal, context.resources.displayMetrics).toInt()
        }

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * sp转px
         *
         * @param context
         * @param spVal
         * @return
         */
        fun sp2px(context: Context, spVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    spVal, context.resources.displayMetrics).toInt()
        }

        fun isOver600dp(context: Context): Boolean {
            val resources = context.resources
            val displayMetrics = resources.displayMetrics
            return displayMetrics.widthPixels / displayMetrics.density >= 600
        }

        /**
         * px转dp
         *
         * @param context
         * @param pxVal
         * @return
         */
        fun px2dp(context: Context, pxVal: Float): Float {
            val scale = context.resources.displayMetrics.density
            return pxVal / scale
        }

        /**
         * px值转换成dp值
         *
         * @param pxValue px值
         * @return dp值
         */
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * px转sp
         *
         * @param context
         * @param pxVal
         * @return
         */
        fun px2sp(context: Context, pxVal: Float): Float {
            return pxVal / context.resources.displayMetrics.scaledDensity
        }

        /**
         * 获取屏幕分辨率
         *
         * @param context
         * @return
         */
        fun getPhoneSize(context: Context): String {
            val dm: DisplayMetrics
            dm = context.resources.displayMetrics

            /* int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;*/
            val screenWidth = dm.widthPixels // 屏幕宽（像素，如：480px）
            val screenHeight = dm.heightPixels // 屏幕高（像素，如：800px）

            return "$screenWidth*$screenHeight"
        }



        fun getDisplayMetrics(context: Context): DisplayMetrics {
            val metric = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(metric)
            return metric
        }




        /**
         * 保持屏幕常亮
         */
        fun keepBright(activity: Activity) {
            //需在setContentView前调用
            val keepScreenOn = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            activity.window.setFlags(keepScreenOn, keepScreenOn)
        }

        /**
         * 获取屏幕的高度
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val manager = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            return display.height
        }

        /**
         * 获得屏幕高度
         *
         * @param context
         * @return
         */
        fun getScreenWidth(context: Context): Int {
            val wm = context
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

        fun dpToPx(context: Context, dipValue: Float): Int {
            val scale = context.applicationContext.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }


        /**
         * 获取状态栏的高度
         */
        fun getStatusHeight(context: Context): Int {
            var statusHeight = -1
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val `object` = clazz.newInstance()
                val height = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(`object`).toString())
                statusHeight = context.applicationContext.resources.getDimensionPixelSize(height)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return statusHeight
        }

        /**
         * 获取屏幕宽高
         */
        fun getScreenWH(context: Context): IntArray {
            val manager = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            manager.defaultDisplay.getMetrics(outMetrics)
            return intArrayOf(outMetrics.widthPixels, outMetrics.heightPixels)
        }

        /**
         * 隐藏软键盘
         */
        fun hide(dialog: Dialog) {
            val view = dialog.currentFocus
            if (view is TextView) {
                val mInputMethodManager = dialog.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mInputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
            }
        }
    }
}
