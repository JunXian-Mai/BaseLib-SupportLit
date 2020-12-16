package org.markensic.baselibrary.api.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import org.markensic.baselibrary.global.AppGlobal.Companion.sApplication

object DisPlayUtils {
  val physicsDm by lazy {
    val dm = DisplayMetrics()
    (sApplication.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(
      dm
    )
    dm
  }

  fun getPhysicsScreenDensity() = physicsDm.densityDpi

  fun getPhysicsScreenWidth() = physicsDm.widthPixels

  fun getPhysicsScreenHeight() = physicsDm.heightPixels
}

fun Int.px2dip() = this / DisPlayUtils.physicsDm.density + 0.5f

fun Float.dp2px() = (DisPlayUtils.physicsDm.density * this).toInt()