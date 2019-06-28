package com.yk.silence.customwallpaper.util

object StringUtil {

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return `true`: null或全空格<br></br> `false`: 不为null且不全空格
     */
    fun isSpace(s: String?): Boolean {
        return s == null || s.trim { it <= ' ' }.length == 0
    }
}