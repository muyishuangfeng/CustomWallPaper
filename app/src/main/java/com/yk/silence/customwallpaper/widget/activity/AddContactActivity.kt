package com.yk.silence.customwallpaper.widget.activity

import android.view.View
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContactActivity : BaseActivity(), View.OnClickListener, CustomTitleBar.TitleClickListener {

    override fun getLayoutID(): Int {
        return R.layout.activity_add_contact
    }

    override fun initData() {
        title_add_contact.setTitleClickListener(this)
        ryt_contacts.setOnClickListener(this)
        ryt_scan.setOnClickListener(this)
        ryt_weChat.setOnClickListener(this)
        ryt_qq.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ryt_contacts -> {
                startActivity(ContactListActivity::class.java)
            }
            R.id.ryt_scan -> {

            }
            R.id.ryt_weChat -> {

            }
            R.id.ryt_qq -> {

            }
        }
    }

    override fun onLeftClick() {
        onBackPressed()
    }

    override fun onRightClick() {
    }
}
