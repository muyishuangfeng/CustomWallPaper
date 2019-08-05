package com.yk.silence.customwallpaper.mvp.presenter

import android.content.Context
import com.yk.silence.customwallpaper.mvp.contract.AppContract
import com.yk.silence.customwallpaper.mvp.model.AppModel

class AppPresenter(context: Context, view: AppContract.View) : AppContract.Presenter {

    var mContext: Context? = null
    var mView: AppContract.View? = null
    val mModel: AppModel by lazy {
        AppModel()
    }

    init {
        this.mContext = context
        this.mView = view
    }


    override fun onStart() {
        requestData()
    }

    override fun requestData() {
        mModel.loadData(mContext!!)
    }

}
