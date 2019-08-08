package com.yk.silence.customwallpaper.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View

import com.yk.silence.customwallpaper.R
import androidx.appcompat.app.AlertDialog
import com.yk.silence.customwallpaper.impl.OnPictureSelectListener
import kotlinx.android.synthetic.main.dialog_picture_layout.*


class PictureSelectDialog(context: Context) : AlertDialog(context, R.style.ActionSheet), View.OnClickListener {

    internal var mListener: OnPictureSelectListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_picture_layout)
        //设置背景透明，不然会出现白色直角问题
        val window = window!!
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        //初始化布局控件
        initView()
        //确定和取消按钮的事件监听
        //设置参数必须在show之后，不然没有效果
        val params = getWindow()!!.attributes
        params.y = -1000
        params.gravity = Gravity.BOTTOM
        getWindow()!!.attributes = params
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        //对话框标题
        txt_dialog_picture.setOnClickListener(this)
        txt_dialog_camera.setOnClickListener(this)
        txt_dialog_cancel.setOnClickListener(this)
    }




    override fun onClick(view: View) {
        when (view.id) {
            R.id.txt_dialog_cancel -> {//取消
                dismiss()
            }
            R.id.txt_dialog_camera -> {//相机
                if (mListener != null) {
                    mListener!!.onCameraSelect()
                }
                dismiss()
            }
            R.id.txt_dialog_picture -> {//图片
                if (mListener != null) {
                    mListener!!.onPictureSelect()
                }
                dismiss()
            }

        }
    }



    fun setPictureSelectListener(confirmListener: OnPictureSelectListener) {
        this.mListener = confirmListener
    }


}
