package org.markensic.baselibrary.api.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import org.markensic.baselibrary.global.AppGlobal.Companion.sApplication

object DisPlayUtil {
    val physicsDm by lazy {
        val dm = DisplayMetrics()
        (sApplication.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(dm)
        dm
    }

    fun getPhysicsScreenDensity(): Int {
        return physicsDm.densityDpi
    }

    fun getPhysicsScreenWidth(): Int {
        return physicsDm.widthPixels
    }

    fun getPhysicsScreenHeight(): Int {
        return physicsDm.heightPixels
    }

    fun dp2px(size: Int): Int {
        return (physicsDm.density * size + 0.5f).toInt()
    }

    fun dp2px(size: Float): Int {
        return (physicsDm.density * size).toInt()
    }

    fun px2dip(size: Int): Int {
        return (size / physicsDm.density + 0.5f).toInt()
    }
}