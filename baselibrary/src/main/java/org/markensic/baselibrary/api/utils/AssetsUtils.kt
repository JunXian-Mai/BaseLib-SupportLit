package org.markensic.baselibrary.api.utils

import android.content.ContextWrapper
import java.io.InputStream

object AssetsUtils {
  fun <R> open(cw: ContextWrapper, name: String, block: (InputStream) -> R): R {
    return cw.assets.open(name).use(block)
  }

  fun open(cw: ContextWrapper, name: String): String {
    return cw.assets.open(name).use {
      it.bufferedReader().readText()
    }
  }
}
