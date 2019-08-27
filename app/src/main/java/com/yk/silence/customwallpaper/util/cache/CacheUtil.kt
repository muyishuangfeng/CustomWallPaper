package com.yk.silence.customwallpaper.util.cache

import com.tencent.mmkv.MMKV
import com.yk.silence.customwallpaper.constance.APP
import com.yk.silence.customwallpaper.constance.Constants


@Suppress("UNCHECKED_CAST", "UNREACHABLE_CODE")
class CacheUtil private constructor() {

    private var mKV: MMKV? = null

    init {
        MMKV.initialize(Constants.CACHE_PATH)
        mKV = MMKV.defaultMMKV()
    }

    companion object {
        /**
         * 单例
         */
        val instance: CacheUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CacheUtil()
        }
    }


    /**
     * 存储
     */
    fun putValue(key: String?, value: Any?) {
        when (value) {
            is Int -> mKV?.encode(key, value)
            is Boolean -> mKV?.encode(key, value)
            is Float -> mKV?.encode(key, value)
            is String -> mKV?.encode(key, value)
            is Long -> mKV?.encode(key, value)
            is ByteArray -> mKV?.encode(key, value)
        }
    }

    /**
     * 获取
     */
    fun <T> getValue(key: String?, type: Class<T>): T {
        when (type) {
            kotlin.Int::class.java -> {
                val value = mKV?.decodeInt(key)
                return value as T
            }
            Double::class.java -> {
                val value = mKV?.decodeDouble(key)
                return value as T
            }
            Boolean::class.java -> {
                val value = mKV?.decodeBool(key)
                return value as T
            }
            Float::class.java -> {
                val value = mKV?.decodeFloat(key)
                return value as T
            }
            String::class.java -> {
                val value = mKV?.decodeString(key)
                return value as T
            }
            Long::class.java -> {
                val value = mKV?.decodeLong(key)
                return value as T
            }
        }
        return null as T
    }

    /**
     * 移除
     */
    fun remove(key: String) {
        mKV?.removeValueForKey(key)
    }
}



