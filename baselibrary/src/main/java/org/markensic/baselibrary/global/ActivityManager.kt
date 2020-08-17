package org.markensic.baselibrary.global

import android.app.Activity
import org.markensic.baselibrary.impl.ActivityLifecycle
import java.lang.ref.WeakReference

object ActivityManager: ActivityLifecycle {
    var weakCurrentActivity: WeakReference<Activity>? = null

    override fun onActivityResumed(p0: Activity) {
        super.onActivityResumed(p0)
        weakCurrentActivity.also {
            it?.clear()
            weakCurrentActivity = WeakReference<Activity>(p0)
        }
    }
}