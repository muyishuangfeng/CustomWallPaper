package com.yk.silence.customwallpaper.net.api

import android.content.Context
import com.yk.silence.customwallpaper.net.retrofit.RetrofitClient

class Api {

    fun getApi(context: Context): ApiService {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService!!
    }

}
