package com.yk.silence.customwallpaper.constance

import androidx.multidex.MultiDex
import org.litepal.LitePal
import org.litepal.LitePalApplication

class APP:LitePalApplication(){

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        MultiDex.install(this);
    }

}
