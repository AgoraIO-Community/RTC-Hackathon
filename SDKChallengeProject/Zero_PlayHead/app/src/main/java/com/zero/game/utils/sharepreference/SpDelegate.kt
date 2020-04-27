package com.zero.game.utils.sharepreference

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.zero.game.BaseApp
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *  eg. var username : String by SpDelegate("username","linda")
 */

class SpDelegate<T>(private val name: String, private val default: T) : ReadWriteProperty<Any?, T> {

    private val prefs: SharedPreferences by lazy {
        BaseApp.instance.applicationContext.getSharedPreferences("app_share_preference", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharedPreferences(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putSharedPreferences(name, value)
    }

    @SuppressLint("CommitPrefEdits")
    private fun putSharedPreferences(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("SharedPreferences can't save this type")
        }
    }.apply()

    @Suppress("UNCHECKED_CAST")
    private fun getSharedPreferences(name: String, default: T): T = with(prefs) {
        val res = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("SharedPreferences can't be get this type")
        }
        return res as T
    }

}