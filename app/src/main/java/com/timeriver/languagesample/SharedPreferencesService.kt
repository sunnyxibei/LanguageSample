package com.timeriver.languagesample

import android.content.Context

class SharedPreferencesService {

    private val sp = App.appContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    var language: String
        get() = getString(KEY_LANGUAGE)
        set(value) {
            putString(KEY_LANGUAGE, value)
        }

    fun putInt(key: String, value: Int) {
        sp.edit().putInt(key, value).apply()
    }

    fun putString(key: String, value: String) {
        if (getString(key) != value) {
            sp.edit().putString(key, value).apply()
        }
    }

    fun putBoolean(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).apply()
    }

    fun putFloat(key: String, value: Float) {
        if (getFloat(key) != value) {
            sp.edit().putFloat(key, value).apply()
        }
    }

    fun putLong(key: String, value: Long) {
        sp.edit().putLong(key, value).apply()
    }

    fun putStringSet(key: String, value: Set<String>) {
        sp.edit().putStringSet(key, value).apply()
    }

    fun getInt(key: String) =
        sp.getInt(key, 0)

    fun getString(key: String, defValue: String = "") =
        sp.getString(key, defValue)!!

    fun getBoolean(key: String, defValue: Boolean = false) =
        sp.getBoolean(key, defValue)

    fun getFloat(key: String) =
        sp.getFloat(key, 0F)

    fun getLong(key: String) =
        sp.getLong(key, 0)

    fun getStringSet(key: String) =
        sp.getStringSet(key, mutableSetOf())

    companion object {
        val instance = SharedPreferencesService()
        private const val SP_NAME = "language_sample"
        private const val KEY_LANGUAGE = "key_language"
    }
}