package com.yk.silence.customwallpaper.mvp.model.bean

import android.graphics.drawable.Drawable

class AppBean {
    var path: String? = null
    var version: String? = null
    var size: String? = null
    var name: String? = null
    var packageName: String? = null
    var installed: Boolean = false
    var icon: Drawable? = null
}