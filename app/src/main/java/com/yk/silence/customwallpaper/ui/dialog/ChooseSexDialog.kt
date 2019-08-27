package com.yk.silence.customwallpaper.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View

import com.yk.silence.customwallpaper.R
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_sex_layout.*


class ChooseSexDialog(context: Context) : AlertDialog(context, R.style.ActionSheet), View.OnClickListener {

    internal var mListener: OnSexClickListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_sex_layout)
        //设置背景透明，不然会出现白色直角问题
        val window = window!!
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //初始化布局控件
        initView()
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        //男
        txt_dialog_boy.setOnClickListener(this)
        //女
        txt_dialog_girl.setOnClickListener(this)

    }




    override fun onClick(view: View) {
        when (view.id) {
            R.id.txt_dialog_boy -> {//男
                if (mListener != null) {
                    mListener!!.onBoyClick()
                }
                dismiss()
            }
            R.id.txt_dialog_girl -> {//女
                if (mListener != null) {
                    mListener!!.onGirlClick()
                }
                dismiss()
            }

        }
    }

    /**
     * 接口
     */
    interface OnSexClickListener {
        fun onGirlClick()

        fun onBoyClick()
    }

    /**
     * 回调
     */
    fun setOnSexClickListener(confirmListener: OnSexClickListener) {
        this.mListener = confirmListener
    }

}
