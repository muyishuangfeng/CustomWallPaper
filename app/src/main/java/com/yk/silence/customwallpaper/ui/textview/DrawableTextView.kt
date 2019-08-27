package com.yk.silence.customwallpaper.ui.textview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet

import com.yk.silence.customwallpaper.R
import androidx.appcompat.widget.AppCompatTextView


class DrawableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {
    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private val mDrawable: Drawable?
    private var mLocation: Int = 0

    init {
        val a = context.obtainStyledAttributes(attrs,
                R.styleable.DrawableTextView)
        mWidth = a
                .getDimensionPixelSize(R.styleable.DrawableTextView_drawable_width, 0)
        mHeight = a.getDimensionPixelSize(R.styleable.DrawableTextView_drawable_height,
                0)
        mDrawable = a.getDrawable(R.styleable.DrawableTextView_drawable_src)
        mLocation = a.getInt(R.styleable.DrawableTextView_drawable_location, LEFT)
        a.recycle()
        //绘制Drawable宽高,位置
        drawDrawable(mLocation)
    }


    /**
     * 绘制图片
     */
    fun drawDrawable(mLocation: Int) {
        if (mDrawable != null) {
            val bitmap = (mDrawable as BitmapDrawable).bitmap
            val drawable: Drawable
            if (mWidth != 0 && mHeight != 0) {
                drawable = BitmapDrawable(resources, getBitmap(bitmap,
                        mWidth, mHeight))

            } else {
                drawable = BitmapDrawable(resources,
                        Bitmap.createScaledBitmap(bitmap, bitmap.width,
                                bitmap.height, true))
            }

            when (mLocation) {
                LEFT -> this.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
                TOP -> this.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null)
                RIGHT -> this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        drawable, null)
                BOTTOM -> this.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                        drawable)
            }
        }
    }

    /**
     * 获取图片
     */
    fun getBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        // 获得图片的宽高
        val width = bm.width
        val height = bm.height
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
    }


    fun setLocation(mLocation: Int) {
        this.mLocation = mLocation
    }

    override fun setWidth(width: Int) {
        this.mWidth = width
    }

    override fun setHeight(height: Int) {
        this.mHeight = height
    }

    companion object {

        const val LEFT = 1
        const val TOP = 2
        const val RIGHT = 3
        const val BOTTOM = 4
    }

}
