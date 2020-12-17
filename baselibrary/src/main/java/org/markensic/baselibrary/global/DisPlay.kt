package org.markensic.baselibrary.global

import org.markensic.baselibrary.api.utils.DisPlayUtils

fun Int.px2dip() = this / DisPlayUtils.physicsDm.density + 0.5f

fun Float.dp2px() = (DisPlayUtils.physicsDm.density * this).toInt()
