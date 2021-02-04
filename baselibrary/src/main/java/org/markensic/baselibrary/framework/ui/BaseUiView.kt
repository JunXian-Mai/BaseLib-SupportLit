package org.markensic.baselibrary.framework.ui

import android.app.Activity
import android.view.View

interface BaseUiView {
  fun <T : View> View.bindView(res: Int): Lazy<T> {
    return lazy { findViewById<T>(res) }
  }

  fun <T : View> Activity.bindView(res: Int): Lazy<T> {
    return lazy { findViewById<T>(res) }
  }
}
