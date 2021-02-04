package org.markensic.baselibrary.global


fun <T> tryCatch(process: () -> T): String? {
  var errorMsg: String? = null
  try {
    process()
  } catch (e: Exception) {
    errorMsg = """
      msg: ${e.message}
      loc: ${e.localizedMessage}
      cause: ${e.cause}
      excep: $e
    """.trimIndent()
  } finally {
    return errorMsg
  }
}
