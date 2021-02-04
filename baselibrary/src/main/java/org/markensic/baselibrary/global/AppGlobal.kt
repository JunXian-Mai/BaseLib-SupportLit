package org.markensic.baselibrary.global

import android.app.Application
import org.markensic.baselibrary.api.delegate.SharedPreferencesMapDelegate

class AppGlobal {
  companion object {
    private const val KEY = "_mSicGlobal"
    private const val FIRST_START_KEY = "${KEY}_First_Start"
    private val sp = SharedPreferencesMapDelegate(KEY)

    val sLogTag = sApplication.packageName
    val sFirstStart: Boolean by lazy {
      sp[FIRST_START_KEY].let {
        when (it) {
          is Boolean -> it
          else -> {
            sp[FIRST_START_KEY] = false
            true
          }
        }
      }
    }

    private var _application: Application? = null
    val sApplication: Application
      get() {
        if (_application == null) {
          val actThread = Class.forName("android.app.ActivityThread")
          _application =
            actThread.getDeclaredMethod("currentApplication").invoke(null) as Application
        }
        return _application ?: throw Exception("NotFound this Application")
      }
  }
}
