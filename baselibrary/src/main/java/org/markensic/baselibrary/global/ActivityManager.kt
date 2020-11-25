package org.markensic.baselibrary.global

import android.app.Activity
import android.os.Bundle
import org.markensic.baselibrary.impl.ActivityLifecycle
import java.lang.ref.WeakReference
import java.util.*

object ActivityManager: ActivityLifecycle {
    val stack: Stack<WeakReference<Activity>> = Stack()

    var weakCurrentActivity: WeakReference<Activity>? = null

    var weakLasterActivity: WeakReference<Activity>? = null

    override fun onActivityCreated(p0: Activity, savedInstanceState: Bundle?) {
        super.onActivityCreated(p0, savedInstanceState)
        stack.push(WeakReference(p0))

        weakLasterActivity.also {
            it?.clear()
            weakLasterActivity = WeakReference(p0)
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

    override fun onActivityDestroyed(p0: Activity) {
        super.onActivityDestroyed(p0)
        stack.pop()
    }

    fun backToStep(step: Int) {
        stack.mapNotNull {
            it.get()
        }.apply {
            for (i in size - 1 downTo step + 1) {
                this[i].finish()
            }
        }
    }
}