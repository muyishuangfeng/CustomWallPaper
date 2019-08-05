package com.yk.silence.customwallpaper.net.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.yk.silence.customwallpaper.net.cache.CacheInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.Exception
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

/**
 * retrofit客户端
 */
class RetrofitClient private constructor(context: Context, baseUrl: String) {
    var httpCacheDirectory: File? = null
    val mContext: Context? = context
    var cache: Cache? = null
    var okHttpClient: OkHttpClient? = null
    var retrofit: Retrofit? = null
    private val DEFAULT_TIMEOUT: Long = 20
    val url = baseUrl

    init {
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(mContext?.cacheDir, "")
        }
        try {
            if (cache == null) {
                cache = Cache(httpCacheDirectory!!, 10 * 1024 * 1024)
            }
        } catch (e: Exception) {
            Log.e("OKHttp", "Could not create http cache", e)
        }
        //创建okHttp
        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(getLoggerInterceptor())
                .addNetworkInterceptor(CacheInterceptor(mContext!!))
                .addInterceptor(CacheInterceptor(mContext))
                .build()
        //创建retrofit
        retrofit = Retrofit.Builder()
                .client(okHttpClient!!)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    /**
     * 日志拦截器
     * 将你访问的接口信息
     *
     * @return 拦截器
     */
    private fun getLoggerInterceptor(): Interceptor {
        //日志显示级别
        val level = HttpLoggingInterceptor.Level.BODY
        //新建log拦截器
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.e("ApiUrl", "--->$message") })
        loggingInterceptor.level = level
        return loggingInterceptor
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        var sInstance: RetrofitClient? = null


        fun getInstance(context: Context, baseUrl: String): RetrofitClient {
            if (sInstance == null) {
                synchronized(RetrofitClient::class.java) {
                    if (sInstance == null) {
                        sInstance = RetrofitClient(context, baseUrl)
                    }
                }
            }
            return sInstance!!

        }
    }

    /**
     * 创建
     */
    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)
    }
}