package org.markensic.baselibrary.global

fun Any.logi(log: String) {
  AppLog.i(this::class.java.simpleName, log)
}

fun Any.loge(log: String) {
  AppLog.e(this::class.java.simpleName, log)
}

fun Any.logd(log: String) {
  AppLog.d(this::class.java.simpleName, log)
}

fun Any.logv(log: String) {
  AppLog.v(this::class.java.simpleName, log)
}

fun Any.logw(log: String) {
  AppLog.w(this::class.java.simpleName, log)
}
