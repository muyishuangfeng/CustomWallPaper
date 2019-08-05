package com.yk.silence.customwallpaper.mvp.contract

import com.yk.silence.customwallpaper.base.impl.BasePresenter
import com.yk.silence.customwallpaper.base.impl.BaseView
import com.yk.silence.customwallpaper.mvp.model.bean.AppBean

interface AppContract {

    interface View : BaseView<Presenter> {

        fun setData(list: MutableList<AppBean>)

        fun install(bean: AppBean)


        fun unInstall(bean: AppBean)

    }

    interface Presenter : BasePresenter {
        fun requestData()
    }

}