package com.yk.silence.customwallpaper.widget.activity

import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.widget.fragment.TimeLineDetailFragment

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UIActivity : BaseActivity() {


    override fun getLayoutID(): Int {
        return R.layout.activity_ui
    }

    override fun initData() {
        if (intent != null && intent.extras != null) {
            val mUrl = intent.extras.getString(Constants.ARG_NUMBER)
            val mFragment =
                    TimeLineDetailFragment.newInstance(mUrl!!)
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fl_container, mFragment)
                    .commit()
        }


    }

}
