package com.yk.silence.customwallpaper.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager

/**
 * Created by masel on 2016/10/10.
 */

object WifiUtils {
    /**
     * 获取IP
     */
    fun getWifiIp(context: Context): String? {
        val wifimanager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifimanager.connectionInfo
        return if (wifiInfo != null) {
            intToIp(wifiInfo.ipAddress)
        } else null
    }

    private fun intToIp(i: Int): String {
        return (i and 0xFF).toString() + "." + (i shr 8 and 0xFF) + "." + (i shr 16 and 0xFF) + "." + (i shr 24 and 0xFF)
    }

    /**
     * 获取wifi状态
     */
    fun getWifiState(context: Context): Int {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager?.wifiState ?: WifiManager.WIFI_STATE_DISABLED
    }

    /**
     * 获取wifi连接状态
     */
    fun getWifiConnectState(context: Context): NetworkInfo.State {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mWiFiNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return if (mWiFiNetworkInfo != null) {
            mWiFiNetworkInfo.state
        } else NetworkInfo.State.DISCONNECTED
    }
}
