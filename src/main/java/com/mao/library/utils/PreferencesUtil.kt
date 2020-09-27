package com.mao.library.utils

import android.content.Context
import android.content.SharedPreferences
import com.mao.library.abs.AbsApplication


/**
 * Created by maozonghong
 * on 2019/12/18
 */

object PreferencesUtil {
    private val mPreferences: SharedPreferences by lazy {
        AbsApplication.getInstance()
            .getSharedPreferences(AbsApplication.APPNAME, Context.MODE_MULTI_PROCESS)
    }


    fun contains(key: String?): Boolean {
        return mPreferences.contains(key)
    }

    fun clear() {
        val editor = mPreferences.edit()
        editor.clear()
        editor.commit()
    }

    fun delete(name: String): Boolean {
        val editor = mPreferences.edit()
        editor.remove(name)
        return editor.commit()
    }

    @JvmStatic
    fun readString(name: String): String? {
        return mPreferences.getString(name, "")
    }

    fun readLong(name: String): Long {
        return mPreferences.getLong(name, 0)
    }

    fun readInt(name: String, defaultval: Int): Int {
        return mPreferences.getInt(name, defaultval)
    }

    fun readFloat(name: String, defaultval: Float): Float {
        return mPreferences.getFloat(name, defaultval)
    }

    fun readString(name: String, defaultVal: String?): String? {
        return mPreferences.getString(name, defaultVal)
    }

    fun readBoolean(name: String, defaultVal: Boolean): Boolean {
        return mPreferences.getBoolean(name, defaultVal)
    }

    fun writeString(name: String, value: String?): Boolean {
        val editor = mPreferences.edit()
        editor.putString(name, value)
        return editor.commit()
    }

    fun writeLong(name: String, value: Long): Boolean {
        val editor = mPreferences.edit()
        editor.putLong(name, value)
        return editor.commit()
    }

    fun writeInt(name: String, value: Int): Boolean {
        val editor =mPreferences.edit()
        editor.putInt(name, value)
        return editor.commit()
    }

    fun writeFloat(name: String, value: Float): Boolean {
        val editor =mPreferences.edit()
        editor.putFloat(name, value)
        return editor.commit()
    }

    fun writeBoolean(name: String, value: Boolean): Boolean {
        val editor =mPreferences.edit()
        editor.putBoolean(name, value)
        return editor.commit()
    }
}
