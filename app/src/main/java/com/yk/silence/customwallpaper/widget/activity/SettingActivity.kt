package com.yk.silence.customwallpaper.widget.activity

import android.view.View
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.constance.AppConfig
import com.yk.silence.customwallpaper.util.UserSettingUtil
import com.yk.silence.customwallpaper.util.cache.CacheUtil
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity(), View.OnClickListener, CustomTitleBar.TitleClickListener {


    override fun getLayoutID(): Int {
        return R.layout.activity_setting
    }

    override fun initData() {
        title_setting.setTitleClickListener(this)
        lyt_setting_nickName.setOnClickListener(this)
        lyt_setting_area.setOnClickListener(this)
        lyt_setting_birthday.setOnClickListener(this)
        lyt_setting_sex.setOnClickListener(this)
        getData()
    }


    override fun onLeftClick() {
        onBackPressed()
    }

    override fun onRightClick() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.lyt_setting_nickName -> {

            }
            R.id.lyt_setting_area -> {
                UserSettingUtil.chooseArea(this, resources.getString(R.string.text_province),
                        resources.getString(R.string.text_city), resources.getString(R.string.text_discrict),
                        txt_setting_area)
            }
            R.id.lyt_setting_birthday -> {
                UserSettingUtil.chooseDate(this, txt_setting_birthday)
            }
            R.id.lyt_setting_sex -> {
                UserSettingUtil.chooseSex(this, txt_setting_sex)
            }

        }
    }


    /**
     * 获取数据
     */
    private fun getData() {
        txt_setting_sex.text = CacheUtil.instance.getValue(AppConfig.USER_SEX, String::class.java)
        txt_setting_nickName.text = CacheUtil.instance.getValue(AppConfig.USER_NICKNAME, String::class.java)
        txt_setting_birthday.text = CacheUtil.instance.getValue(AppConfig.USER_BIRTHDAY, String::class.java)
        txt_setting_area.text = CacheUtil.instance.getValue(AppConfig.USER_AREA, String::class.java)
    }
}
