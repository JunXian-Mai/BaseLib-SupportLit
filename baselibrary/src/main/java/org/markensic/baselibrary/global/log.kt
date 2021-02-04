package org.markensic.baselibrary.global

fun <T : Any> T.logi(log: String) {
  AppLog.i(getTag(this), log)
}

fun <T : Any> T.loge(log: String) {
  AppLog.e(getTag(this), log)
}

fun <T : Any> T.logd(log: String) {
  AppLog.d(getTag(this), log)
}

fun <T : Any> T.logv(log: String) {
  AppLog.v(getTag(this), log)
}

fun <T : Any> T.logw(log: String) {
  AppLog.w(getTag(this), log)
}

fun <T : String?> T.print() {
  val string = this as String?
  if (string?.isNotBlank() == true) {
    AppLog.i("", string)
  }
}


private fun getTag(any: Any): String {
  var name = any::class.java.name
  if (name.contains("$")) {
    name = name.split("$")[0]
  }
  val simpleName = any::class.java.simpleName
  return if (simpleName.isNotBlank()) {
    simpleName
  } else {
    name
  }
}
