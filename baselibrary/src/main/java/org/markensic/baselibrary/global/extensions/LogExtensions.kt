package org.markensic.baselibrary.global.extensions

import org.markensic.baselibrary.global.AppLog

fun <T : Any> T.logi(log: String) {
  AppLog.i(this::class.java.simpleName, log)
}

fun <T : Any> T.loge(log: String) {
  AppLog.e(this::class.java.simpleName, log)
}

fun <T : Any> T.logd(log: String) {
  AppLog.d(this::class.java.simpleName, log)
}

fun <T : Any> T.logv(log: String) {
  AppLog.v(this::class.java.simpleName, log)
}

fun <T : Any> T.logw(log: String) {
  AppLog.w(this::class.java.simpleName, log)
}
