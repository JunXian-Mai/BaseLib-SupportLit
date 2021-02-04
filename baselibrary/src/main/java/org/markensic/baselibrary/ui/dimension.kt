package org.markensic.baselibrary.ui

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

val Float.dp
  get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    Resources.getSystem().displayMetrics
  )

val Float.sp
  get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    this,
    Resources.getSystem().displayMetrics
  )

val Int.px
  get() =
    this

val Int.dp
  get() =
    this.toFloat().dp

val Int.sp
  get() =
    this.toFloat().sp

fun Int.px2Dip() =
  dimensionApply(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    Resources.getSystem().displayMetrics
  )

fun Int.px2Sp() = dimensionApply(
  TypedValue.COMPLEX_UNIT_SP,
  this,
  Resources.getSystem().displayMetrics
)

fun Float.sin(angdeg: Float) = (this * kotlin.math.sin(Math.toRadians(angdeg.toDouble()))).toFloat()

fun Float.cos(angdeg: Float) = (this * kotlin.math.cos(Math.toRadians(angdeg.toDouble()))).toFloat()

fun Double.sin(angdeg: Double) = this * kotlin.math.sin(Math.toRadians(angdeg))

fun Double.cos(angdeg: Double) = this * kotlin.math.cos(Math.toRadians(angdeg))

fun dimensionApply(
  unit: Int, value: Int,
  metrics: DisplayMetrics
): Float {
  when (unit) {
    TypedValue.COMPLEX_UNIT_PX -> return value.toFloat()
    TypedValue.COMPLEX_UNIT_DIP -> return value / metrics.density
    TypedValue.COMPLEX_UNIT_SP -> return value / metrics.scaledDensity
    TypedValue.COMPLEX_UNIT_PT -> return value / (metrics.xdpi * (1.0f / 72))
    TypedValue.COMPLEX_UNIT_IN -> return value / metrics.xdpi
    TypedValue.COMPLEX_UNIT_MM -> return value / (metrics.xdpi * (1.0f / 25.4f))
  }
  return 0f
}





