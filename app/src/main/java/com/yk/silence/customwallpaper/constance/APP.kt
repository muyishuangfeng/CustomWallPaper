package com.yk.silence.customwallpaper.constance

import org.litepal.LitePal
import org.litepal.LitePalApplication

class APP:LitePalApplication(){

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
    }

}
