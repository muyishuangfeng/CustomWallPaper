package com.yk.silence.customwallpaper.util.cache

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

import com.google.gson.Gson
import com.yk.silence.customwallpaper.util.cache.SPUtils.Companion.PREFERENCE_NAME
import com.yk.silence.customwallpaper.util.cache.SPUtils.Companion.preferenceManagerHashMap

import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap

class SPUtils private constructor(context: Context) {
    private val gson: Gson
    private val sharedPreference: SharedPreferences

    init {
        sharedPreference = context.applicationContext
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        gson = Gson()
    }


    fun putValue(context: Context, key: String, value: Any): SPUtils {
        val settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        val editor = settings.edit()
        when (value) {
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is String -> editor.putString(key, value)
            is Long -> editor.putLong(key, value)
            else -> {
                val json = gson.toJson(value)
                editor.putString(key, json)
            }
        }
        editor.apply()
        return this
    }

    /**
     * Get value which is saved
     */
    fun <T> getValue(key: String, type: Class<T>): T? {
        when (type) {
            Int::class.java -> {
                val value = sharedPreference.getInt(key, 0)
                return value as T
            }
            Boolean::class.java -> {
                val value = sharedPreference.getBoolean(key, false)
                return value as T
            }
            Float::class.java -> {
                val value = sharedPreference.getFloat(key, 0f)
                return value as T
            }
            String::class.java -> {
                val value = sharedPreference.getString(key, "")
                return value as T
            }
            Long::class.java -> {
                val value = sharedPreference.getLong(key, 0)
                return value as T
            }
            else -> {
                val json = sharedPreference.getString(key, "")
                return if (TextUtils.isEmpty(json)) {
                    null
                } else {
                    gson.fromJson(json, type)
                }
            }
        }
    }

    /**
     * Get value which is saved with a default value
     */
    fun <T> getValue(key: String, type: Class<T>, defaultValue: T): T? {
        return if (sharedPreference.contains(key)) {
            getValue(key, type)
        } else {
            defaultValue
        }
    }

    /**
     * Get List of object which is saved in [SharedPreferences]
     */
    fun <T> getValues(key: String, clazz: Class<Array<T>>): List<T>? {
        val json = sharedPreference.getString(key, "")
        if (TextUtils.isEmpty(json)) return null
        val values = gson.fromJson(json, clazz)
        val list = ArrayList<T>()
        if (values != null) {
            list.addAll(Arrays.asList(*values))
        }
        return list
    }

    fun removeKey(context: Context, key: String) {
        val settings = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.remove(key).apply()
    }

    companion object {

        private val PREFERENCE_NAME = "CatchUAndroid"
        private var preferenceManagerHashMap: HashMap<String, SPUtils>? = HashMap()

        private fun getSharedPreferenceManager(context: Context, sharedPreferenceName: String): SPUtils {
            if (preferenceManagerHashMap == null) preferenceManagerHashMap = HashMap()
            val name = if (TextUtils.isEmpty(sharedPreferenceName)) PREFERENCE_NAME else sharedPreferenceName
            var sharedPreferencesManager = preferenceManagerHashMap!![name]
            if (sharedPreferencesManager == null) {
                sharedPreferencesManager = SPUtils(context)
                preferenceManagerHashMap!![name] = sharedPreferencesManager
            }
            return sharedPreferencesManager
        }

        @Synchronized
        fun init(context: Context, createDefaultPreference: Boolean, vararg names: String) {
            if (createDefaultPreference) {
                getSharedPreferenceManager(context, PREFERENCE_NAME)
            }
            if (names == null || names.size == 0) return
            for (name in names) {
                getSharedPreferenceManager(context, name)
            }
        }


        val instance: SPUtils
            @Synchronized get() = preferenceManagerHashMap!![PREFERENCE_NAME]
                    ?: throw IllegalStateException("The default share preference is not initialized before. You have to initialize it first by calling init(Context, boolean, String...) function")

        /**
         * put string preferences
         *
         * @param context context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putString(context: Context, key: String, value: String): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putString(key, value)
            return editor.commit()
        }

        /**
         * get string preferences
         *
         * @param context      context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a string
         */
        @JvmOverloads
        fun getString(context: Context, key: String, defaultValue: String? = null): String? {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getString(key, defaultValue)
        }

        /**
         * put int preferences
         *
         * @param context context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putInt(context: Context, key: String, value: Int): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putInt(key, value)
            return editor.commit()
        }

        /**
         * get int preferences
         *
         * @param context      context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a int
         */
        @JvmOverloads
        fun getInt(context: Context, key: String, defaultValue: Int = -1): Int {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getInt(key, defaultValue)
        }

        /**
         * put long preferences
         *
         * @param context context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putLong(context: Context, key: String, value: Long): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putLong(key, value)
            return editor.commit()
        }

        /**
         * get long preferences
         *
         * @param context      context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a long
         */
        @JvmOverloads
        fun getLong(context: Context, key: String, defaultValue: Long = -1): Long {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getLong(key, defaultValue)
        }

        /**
         * put float preferences
         *
         * @param context context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putFloat(context: Context, key: String, value: Float): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putFloat(key, value)
            return editor.commit()
        }

        /**
         * get float preferences
         *
         * @param context      context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a float
         */
        @JvmOverloads
        fun getFloat(context: Context, key: String, defaultValue: Float = -1f): Float {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getFloat(key, defaultValue)
        }

        /**
         * put boolean preferences
         *
         * @param context context
         * @param key     The name of the preference to modify
         * @param value   The new value for the preference
         * @return True if the new values were successfully written to persistent storage.
         */
        fun putBoolean(context: Context, key: String, value: Boolean): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.putBoolean(key, value)
            return editor.commit()
        }

        /**
         * get boolean preferences
         *
         * @param context      context
         * @param key          The name of the preference to retrieve
         * @param defaultValue Value to return if this preference does not exist
         * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
         * this name that is not a boolean
         */
        @JvmOverloads
        fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            return settings.getBoolean(key, defaultValue)
        }

        fun remove(context: Context, key: String) {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.remove(key)
            editor.apply()
        }

        /**
         * 清除所有
         *
         * @param context
         */
        fun clear(context: Context) {
            val settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            val editor = settings.edit()
            editor.clear().apply()
        }
    }
}

