package com.yk.silence.customwallpaper.util

import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayout



object TabUtil {

    fun reflexFixed(tabLayout: TabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post {
            try {
                //拿到tabLayout的mTabStrip属性
                val mTabStrip = tabLayout.getChildAt(0) as LinearLayout
                for (i in 0 until mTabStrip.childCount) {
                    val tabView = mTabStrip.getChildAt(i)

                    //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                    val mTextViewField = tabView.javaClass.getDeclaredField("mTextView")
                    mTextViewField.setAccessible(true)

                    val mTextView = mTextViewField.get(tabView) as TextView

                    tabView.setPadding(0, 0, 0, 0)

                    //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                    var width = 0
                    width = mTextView.width

                    if (width == 0) {
                        mTextView.measure(0, 0)
                        width = mTextView.measuredWidth
                    }
                    val margin = (tabView.width - width) / 2
                    val dp10 = DensityUtils.px2dip(tabLayout.context, margin.toFloat())
                    //设置tab左右间距  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                    val params = tabView.layoutParams as LinearLayout.LayoutParams
                    params.width = width
                    params.leftMargin = margin
                    params.rightMargin = margin
                    tabView.layoutParams = params

                    tabView.invalidate()
                }

            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }

    }
}