package com.yk.silence.customwallpaper.util

import android.app.Activity
import android.graphics.Point

import java.lang.reflect.Field

import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout

class DrawerUtil {

    /**
     * 设置右边侧滑范围
     */
    private fun setRightDrawerEdgeSize(activity: Activity?, drawerLayout: DrawerLayout?,
                                       displayWidthPercentage: Float) {
        if (activity == null || drawerLayout == null) return
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            val leftDraggerField = drawerLayout.javaClass.getDeclaredField("mRightDragger")//Right
            leftDraggerField.isAccessible = true
            val leftDragger = leftDraggerField.get(drawerLayout) as ViewDragHelper

            // 找到 edgeSizeField 并设置 Accessible 为true
            val edgeSizeField = leftDragger.javaClass.getDeclaredField("mEdgeSize")
            edgeSizeField.isAccessible = true
            val edgeSize = edgeSizeField.getInt(leftDragger)

            // 设置新的边缘大小
            val displaySize = Point()
            activity.windowManager.defaultDisplay.getSize(displaySize)
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (displaySize.x * displayWidthPercentage).toInt()))
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * 设置左边侧滑范围
     */
    private fun setLeftDrawerEdgeSize(activity: Activity?, drawerLayout: DrawerLayout?,
                                      displayWidthPercentage: Float) {
        if (activity == null || drawerLayout == null) return
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            val leftDraggerField = drawerLayout.javaClass.getDeclaredField("mLeftDragger")//Right
            leftDraggerField.isAccessible = true
            val leftDragger = leftDraggerField.get(drawerLayout) as ViewDragHelper

            // 找到 edgeSizeField 并设置 Accessible 为true
            val edgeSizeField = leftDragger.javaClass.getDeclaredField("mEdgeSize")
            edgeSizeField.isAccessible = true
            val edgeSize = edgeSizeField.getInt(leftDragger)

            // 设置新的边缘大小
            val displaySize = Point()
            activity.windowManager.defaultDisplay.getSize(displaySize)
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (displaySize.x * displayWidthPercentage).toInt()))
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * 设置两侧侧滑范围
     */
    fun setDrawerEdgeSize(activity: Activity, drawerLayout: DrawerLayout,
                          displayWidthPercentage: Float) {
        setLeftDrawerEdgeSize(activity, drawerLayout, displayWidthPercentage)
        //setRightDrawerEdgeSize(activity, drawerLayout, displayWidthPercentage)
    }

    companion object {

        private var sInstance: DrawerUtil? = null

        /**
         * 单例
         */
        val instance: DrawerUtil
            get() {
                if (sInstance == null) {
                    synchronized(DrawerUtil::class.java) {
                        if (sInstance == null) {
                            sInstance = DrawerUtil()
                        }
                    }
                }
                return sInstance!!
            }
    }
}
