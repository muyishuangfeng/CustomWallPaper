package com.yk.silence.customwallpaper.widget.activity

import android.view.View
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity(), View.OnClickListener,CustomTitleBar.TitleClickListener {



    override fun getLayoutID(): Int {
        return R.layout.activity_setting
    }

    override fun initData() {
        btn_app.setOnClickListener(this)
        title_setting.setTitleClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_app -> {
                //startActivity(AppActivity::class.java)
                startActivity(FileActivity::class.java)
            }
        }
    }

    override fun onLeftClick() {
        onBackPressed()
    }

    override fun onRightClick() {
    }

}
