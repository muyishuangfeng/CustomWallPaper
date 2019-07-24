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

    /**
     * 判断给定的字符串是否为null或者是空的
     *
     * @param string 给定的字符串
     */
    fun isEmpty(string: String?): Boolean {
        return string == null || "" == string.trim { it <= ' ' }
    }

    fun isBlank(str: String?): Boolean {
        return str == null || str.trim({ it <= ' ' }).length == 0
    }
}