package com.mao.library.abs

import com.mao.library.interfaces.NotRequestValue
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.*

open class AbsRequest {

    open fun getBody(): Map<String, String>{
        init()
        val params = HashMap<String, String>()
        addParams(params,javaClass.fields)
        return params
    }

    open fun getMultipartEntity():MultipartBody{
       return getMultipartEntity(null)
    }

    protected fun init() {

    }

    private fun addParams(map: MutableMap<String, String>, fields: Array<Field>) {
        for (field in fields) {
            if (!Modifier.isStatic(field.modifiers) && !field.isAnnotationPresent(NotRequestValue::class.java)) {
                var any: Any? = null
                try {
                    field.isAccessible = true
                    any = field.get(this)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                if (any!= null) {
                    val key = field.name
                    val value: String
                    value = if (any is Boolean) {
                        if (any) "1" else "0"
                    } else if (any is File) {
                        onGetFileFiled(map, key,any)
                        continue
                    } else {
                        any.toString()
                    }
                    map[key] = value
                }
            }
        }
    }

    open fun onGetFileFiled(params: Map<String, String>, key: String, any: File) {}

    private fun getMultipartEntity(extras: MutableMap<String, Any>?): MultipartBody {
        var extras = extras

        if (extras == null) {
            extras = HashMap()
        }
        for (field in javaClass.fields) {
            if (!Modifier.isStatic(field.modifiers) && !field.isAnnotationPresent(NotRequestValue::class.java)) {
                try {
                    field.isAccessible = true
                    val obj = field.get(this)
                    putBody(field.name, obj, extras)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }

            }
        }

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        for ((key, value) in extras) {
            if (value is File) {
                builder.addFormDataPart(key, value.name, RequestBody.create(MultipartBody.FORM, value))
            } else {
                builder.addFormDataPart(key, value.toString())
            }
        }

        return builder.build()
    }

    @Throws(Throwable::class)
    protected fun putBody(name: Any, obj: Any?, extras: MutableMap<String, Any>) {
        if (obj != null) {
            if (obj is File) {
                if (obj.exists()) {
                    extras[name.toString()] = obj
                }
            } else if (obj is Map<*, *>) {
                for ((key, value) in obj) {
                    putBody(key!!, value, extras)
                }
            } else {
                val key = name.toString()
                val value: String = if (obj is Boolean) {
                    if (obj) "1" else "0"
                } else {
                    obj.toString()
                }
                extras[key] = value
                onGetField(key, value)
            }
        }
    }

    private fun onGetField(key: String, value: String) {

    }
}
