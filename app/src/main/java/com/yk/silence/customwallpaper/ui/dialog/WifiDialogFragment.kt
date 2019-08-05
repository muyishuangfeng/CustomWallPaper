package com.yk.silence.customwallpaper.ui.dialog

import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseBottomFragment
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.receiver.WifiConnectChangedReceiver
import com.yk.silence.customwallpaper.util.WifiUtils
import kotlinx.android.synthetic.main.dialog_wifi_layout.*

class WifiDialogFragment : BaseBottomFragment(), View.OnClickListener {


    private var mReceiver = WifiConnectChangedReceiver()

    override fun fragmentId(): Int {
        return R.layout.dialog_wifi_layout
    }

    override fun initView() {
        RxBus.get().register(this)
        checkWifiState(WifiUtils.getWifiConnectState(context!!))
        val intentFilter = IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        context?.registerReceiver(mReceiver, intentFilter)
        btn_wifi_setting.setOnClickListener(this)
        btn_wifi_cancel.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_wifi_setting -> {
                context?.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                dismiss()
            }
            R.id.btn_wifi_cancel -> {
                dismiss()
            }
        }
    }

    /**
     * 隐藏
     */
    override fun dismiss() {
        super.dismiss()
        context?.unregisterReceiver(mReceiver)
        RxBus.get().post(Constants.RxBusEventType.POPUP_MENU_DIALOG_SHOW_DISMISS, Constants.MSG_DIALOG_DISMISS)
        RxBus.get().unregister(this)
    }



    @Subscribe(tags = [Tag(Constants.RxBusEventType.WIFI_CONNECT_CHANGE_EVENT)])
    fun onWifiConnectStateChanged(state: NetworkInfo.State) {
        checkWifiState(state)
    }

    /**
     * 检测wifi状态
     */
    private fun checkWifiState(state: NetworkInfo.State) {
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            if (state == NetworkInfo.State.CONNECTED) {
                val ip = WifiUtils.getWifiIp(context!!)
                if (!TextUtils.isEmpty(ip)) {
                    onWifiConnected(ip!!)
                    return
                }
            }
            onWifiConnecting()
            return
        }
        onWifiDisconnected()
    }

    /**
     * wifi断开
     */
    private fun onWifiDisconnected() {
        txt_wifi_title.text = resources.getString(R.string.text_wanlan_unless)
        txt_wifi_subtitle.visibility = View.VISIBLE
        img_wifi_state.setImageResource(R.drawable.ic_wifi_off)
        txt_wifi_state_hint.text = resources.getString(R.string.fail_to_start_http_service)
        txt_wifi_address.visibility = View.GONE
        btn_wifi_setting.visibility = View.VISIBLE
    }

    /**
     * wifi连接中
     */
    private fun onWifiConnecting() {
        txt_wifi_title.text = resources.getString(R.string.text_wanlan)
        txt_wifi_subtitle.visibility = View.GONE
        img_wifi_state.setImageResource(R.drawable.ic_wifi_on)
        txt_wifi_state_hint.text = resources.getString(R.string.retrofit_wlan_address)
        txt_wifi_address.visibility = View.GONE
        btn_wifi_setting.visibility = View.GONE
    }

    /**
     * wifi连接成功
     */
    private fun onWifiConnected(mIpAddress: String) {
        txt_wifi_title.text = resources.getString(R.string.text_wanlan)
        txt_wifi_subtitle.visibility = View.GONE
        img_wifi_state.setImageResource(R.drawable.ic_wifi_on)
        txt_wifi_state_hint.text = resources.getString(R.string.pls_input_the_following_address_in_pc_browser)
        txt_wifi_address.visibility = View.VISIBLE
        txt_wifi_address.text = String.format(resources.getString(R.string.http_address), mIpAddress, Constants.HTTP_PORT)
        btn_wifi_setting.visibility = View.GONE
    }




}

