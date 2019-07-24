package com.yk.silence.customwallpaper.ui.glide

import com.bumptech.glide.load.model.GlideUrl


/**
 * Created by Silence on 2018/1/5.
 */

class MyGlideUrl(private val mUrl: String) : GlideUrl(mUrl) {

    override fun getCacheKey(): String {
        return mUrl.replace(findTokenParam(), "")
    }

    private fun findTokenParam(): String {
        var tokenParam = ""
        val tokenKeyIndex = if (mUrl.indexOf("?token=") >= 0)
            mUrl.indexOf("?token=")
        else
            mUrl.indexOf("&token=")
        if (tokenKeyIndex != -1) {
            val nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1)
            if (nextAndIndex != -1) {
                tokenParam = mUrl.substring(tokenKeyIndex + 1, nextAndIndex + 1)
            } else {
                tokenParam = mUrl.substring(tokenKeyIndex)
            }
        }
        return tokenParam
    }
}
