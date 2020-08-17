package org.markensic.baselibrary.impl

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.markensic.baselibrary.global.AppGlobal
import org.markensic.baselibrary.global.AppLog

interface ActivityLifecycle: Application.ActivityLifecycleCallbacks {
    override fun onActivityResumed(p0: Activity) {
        p0.also {
            AppLog.i(AppGlobal.sLogTag, "${it::class.java.simpleName} resumed")
        }
    }

    override fun onActivityPaused(p0: Activity) {
        p0.also {
            AppLog.i(AppGlobal.sLogTag, "${it::class.java.simpleName} paused")
        }
    }

    override fun onActivityStarted(p0: Activity) {
        p0.also {
            AppLog.i(AppGlobal.sLogTag, "${it::class.java.simpleName} started")
        }
    }

    override fun onActivityDestroyed(p0: Activity) {
        p0.also {
            AppLog.i(AppGlobal.sLogTag, "${it::class.java.simpleName} destroyed")
        }
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        p0.also {
            AppLog.i(AppGlobal.sLogTag, "${it::class.java.simpleName} saveInstanceState")
        }
    }

    override fun onActivityStopped(p0: Activity) {
        p0.also {
            AppLog.i(AppGlobal.sLogTag, "${it::class.java.simpleName} stopped")
        }
    }

    override fun onActivityCreated(p0: Activity, savedInstanceState: Bundle?) {
        p0.also {
            AppLog.i(AppGlobal.sLogTag, "${it::class.java.simpleName} created")
        }
    }
}