package org.markensic.baselibrary.api.delegate

import android.content.Context
import org.markensic.baselibrary.global.AppGlobal

class SharedPreferencesMapDelegate(spName:String, mode:Int = Context.MODE_PRIVATE):
    HashMap<String, Any?>() {
    val sp = AppGlobal.sApplication.getSharedPreferences(spName, mode).also { it ->
        it.all.forEach { entry ->
            super.put(entry.key, entry.value)
        }
    }

    override fun get(key: String): Any? {
        return super.get(key).let {
            when(it){
                is Int -> {
                    sp.getInt(key, 0)
                }
                is String -> {
                    sp.getString(key, "")
                }
                is Boolean -> {
                    sp.getBoolean(key, false)
                }
                is Float -> {
                    sp.getFloat(key, 0f)
                }
                is Long -> {
                    sp.getLong(key, 0L)
                }
                is Set<*> -> {
                    sp.getStringSet(key, mutableSetOf())
                }
                else -> {
                    null
                }
            }
        }
    }

    override fun put(key: String, value: Any?): Any? {
        return get(key).also {
            when (value) {
                is Int -> {
                    sp.edit().putInt(key, value).apply()
                    super.put(key, value)
                }
                is String -> {
                    sp.edit().putString(key, value).apply()
                    super.put(key, value)
                }
                is Boolean -> {
                    sp.edit().putBoolean(key, value).apply()
                    super.put(key, value)
                }
                is Float -> {
                    sp.edit().putFloat(key, value).apply()
                    super.put(key, value)
                }
                is Long -> {
                    sp.edit().putLong(key, value).apply()
                    super.put(key, value)
                }
                is Set<*> -> {
                    value.iterator().next().also {
                        if (it is String) {
                            sp.edit().putStringSet(key, value as Set<String>?).apply()
                            super.put(key, value)
                        }
                    }
                }
            }
        }
    }
}