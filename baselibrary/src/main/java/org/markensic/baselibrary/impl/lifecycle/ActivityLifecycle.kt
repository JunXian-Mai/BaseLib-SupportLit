package org.markensic.baselibrary.impl.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.markensic.baselibrary.global.AppLog

interface ActivityLifecycle : Application.ActivityLifecycleCallbacks {
  private val tag: String
    get() = "Lifecycle"

  override fun onActivityResumed(p0: Activity) {
    p0.also {
      AppLog.i(tag, "$it resumed")
    }
  }

  override fun onActivityPaused(p0: Activity) {
    p0.also {
      AppLog.i(tag, "$it paused")
    }
  }

  override fun onActivityStarted(p0: Activity) {
    p0.also {
      AppLog.i(tag, "$it started")
    }
  }

  override fun onActivityDestroyed(p0: Activity) {
    p0.also {
      AppLog.i(tag, "$it destroyed")
    }
  }

  override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    p0.also {
      AppLog.i(tag, "$it saveInstanceState")
    }
  }

  override fun onActivityStopped(p0: Activity) {
    p0.also {
      AppLog.i(tag, "$it stopped")
    }
  }

  override fun onActivityCreated(p0: Activity, savedInstanceState: Bundle?) {
    p0.also {
      AppLog.i(tag, "$it created")
    }
  }
}
