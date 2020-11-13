package org.markensic.baselibrary.impl

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import org.markensic.baselibrary.global.AppGlobal
import org.markensic.baselibrary.global.AppLog

abstract class FragmentLifecycle : FragmentManager.FragmentLifecycleCallbacks() {
    private val tag = "Lifecycle"
    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        f.also {
            AppLog.i(tag, "$f attached")
        }
    }

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        f.also {
            AppLog.i(tag, "$f pre created")
        }
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        f.also {
            AppLog.i(tag, "$f created")
        }
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        f.also {
            AppLog.i(tag, "$f activity created")
        }
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        f.also {
            AppLog.i(tag, "$f view created")
        }
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        f.also {
            AppLog.i(tag, "$f started")
        }
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        f.also {
            AppLog.i(tag, "$f resumed")
        }
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        f.also {
            AppLog.i(tag, "$f paused")
        }
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        f.also {
            AppLog.i(tag, "$f stopped")
        }
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        f.also {
            AppLog.i(tag, "$f save instance state")
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        f.also {
            AppLog.i(tag, "$f destroyed")
        }
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        f.also {
            AppLog.i(tag, "$f detached")
        }
    }
}