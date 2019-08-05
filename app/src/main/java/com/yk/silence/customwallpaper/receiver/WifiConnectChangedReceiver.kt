package com.yk.silence.customwallpaper.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Parcelable
import com.hwangjr.rxbus.RxBus
import com.yk.silence.customwallpaper.constance.Constants

class WifiConnectChangedReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION == intent?.action) {
            val parcelableExtra = intent
                    .getParcelableExtra<Parcelable>(WifiManager.EXTRA_NETWORK_INFO)
            if (null != parcelableExtra) {
                val networkInfo = parcelableExtra as NetworkInfo
                RxBus.get().post(Constants.RxBusEventType.WIFI_CONNECT_CHANGE_EVENT, networkInfo.state)
            }
        }
    }
}