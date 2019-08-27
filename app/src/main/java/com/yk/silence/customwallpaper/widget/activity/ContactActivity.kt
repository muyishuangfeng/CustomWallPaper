package com.yk.silence.customwallpaper.widget.activity

import android.view.View
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.activity_contart.*

class ContactActivity : BaseActivity(), View.OnClickListener, CustomTitleBar.TitleClickListener {


    override fun getLayoutID(): Int {
        return R.layout.activity_contart
    }

    override fun initData() {
        title_contact.setTitleClickListener(this)
        txt_contact_msg.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.txt_contact_msg -> {

            }
        }
    }

    override fun onLeftClick() {
        onBackPressed()
    }

    override fun onRightClick() {
        startActivity(AddContactActivity::class.java)
    }

}
