package org.markensic.baselibrary.api.utils

import android.widget.Toast
import org.markensic.baselibrary.global.AppGlobal

object ToastUtils {
    fun show(msg: String) {
        Toast.makeText(AppGlobal.sApplication, msg, Toast.LENGTH_SHORT).show()
    }
}