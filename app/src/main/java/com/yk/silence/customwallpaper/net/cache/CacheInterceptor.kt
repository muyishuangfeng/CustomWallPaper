package com.yk.silence.customwallpaper.net.cache

import android.content.Context
import android.util.Log
import com.yk.silence.customwallpaper.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor(context: Context) : Interceptor {

    private val mContext = context

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (NetworkUtils.isNetConnected(mContext)) {
            val response = chain.proceed(request)
            val maxAge = 60
            val cacheControl = request.cacheControl()
            Log.e("CacheInterceptor", "6s load cahe$cacheControl")
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public,max_age=$maxAge")
                    .build()
        } else {
            Log.e("CacheInterceptor", " no network load cahe")
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            val response = chain.proceed(request)
            val maxStale = 60 * 60 * 24 * 3
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }


    }
}