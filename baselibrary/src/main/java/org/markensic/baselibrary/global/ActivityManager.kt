package org.markensic.baselibrary.global

import android.app.Activity
import android.os.Bundle
import org.markensic.baselibrary.impl.ActivityLifecycle
import java.lang.ref.WeakReference

object ActivityManager: ActivityLifecycle {
    var weakCurrentActivity: WeakReference<Activity>? = null

    var weakLasterActivity: WeakReference<Activity>? = null

    override fun onActivityCreated(p0: Activity, savedInstanceState: Bundle?) {
        super.onActivityCreated(p0, savedInstanceState)
        weakLasterActivity.also {
            it?.clear()
            weakLasterActivity = WeakReference<Activity>(p0)
        }
    }

    override fun onActivityResumed(p0: Activity) {
        super.onActivityResumed(p0)
        weakCurrentActivity.also {
            it?.clear()
            weakCurrentActivity = WeakReference<Activity>(p0)
        }

        weakLasterActivity.also {
            it?.clear()
            weakLasterActivity = WeakReference<Activity>(p0)
        }
    }
}