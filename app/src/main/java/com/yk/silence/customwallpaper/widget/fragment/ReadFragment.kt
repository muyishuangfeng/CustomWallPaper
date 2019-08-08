package com.yk.silence.customwallpaper.widget.fragment

import android.view.View
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseFragment
import com.yk.silence.customwallpaper.widget.activity.SettingActivity
import kotlinx.android.synthetic.main.fragment_read.*


class ReadFragment : BaseFragment(), View.OnClickListener {


    override fun getLayoutID(): Int {
        return R.layout.fragment_read
    }

    override fun initView() {
        btn_setting.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_setting -> {
                startActivity(SettingActivity::class.java)
            }
        }
    }


}
