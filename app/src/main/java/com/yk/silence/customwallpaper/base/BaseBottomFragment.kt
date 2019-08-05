package com.yk.silence.customwallpaper.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout


import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.ui.dialog.CustomBottomSheetDialog

import androidx.coordinatorlayout.widget.CoordinatorLayout


abstract class BaseBottomFragment : BottomSheetDialogFragment() {

    private var mActivity: Activity? = null
    private var mRootView: View? = null
    private var mDialog: CustomBottomSheetDialog? = null

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mActivity = getActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDialog = CustomBottomSheetDialog(activity!!)
    }


    protected val height: Int
        get() = resources.displayMetrics.heightPixels


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDialog = CustomBottomSheetDialog(mActivity!!, R.style.bottomSheetDialogAnimStyle)
        return mDialog!!
    }


    /**
     * 获取布局
     */
    protected abstract fun fragmentId(): Int


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mActivity = context as Activity?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = super.onCreateView(inflater, container, savedInstanceState);
        if (mRootView == null) {
            mRootView = inflater.inflate(fragmentId(), container, false)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mDialog?.setCanceledOnTouchOutside(false)
        val bottomSheet = mDialog?.delegate?.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = height
            bottomSheet.layoutParams = layoutParams
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.peekHeight = height
            // 初始为展开状态
            //behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(view: View, newState: Int) {
                    //禁止拖拽，
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        //设置为收缩状态
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }

                override fun onSlide(view: View, v: Float) {

                }
            })
        }
    }


    /**
     * 初始化控件
     */
    protected abstract fun initView()


    /**
     * 通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    private fun startActivityForResult(cls: Class<*>, bundle: Bundle?,
                                       requestCode: Int) {
        val intent = Intent()
        intent.setClass(mActivity!!, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    @JvmOverloads
    fun startActivity(cls: Class<*>, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(mActivity!!, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 线程
     */
    fun runOnUIThread(r: Runnable?) {
        val activity = mActivity
        if (activity != null && r != null)
            activity.runOnUiThread(r)
    }


}

