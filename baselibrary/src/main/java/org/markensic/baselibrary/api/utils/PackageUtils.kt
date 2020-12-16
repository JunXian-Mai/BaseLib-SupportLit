package org.markensic.baselibrary.api.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import org.markensic.baselibrary.global.AppGlobal
import java.io.File

object PackageUtils {
  val applicationInfo = { flags: Int ->
    AppGlobal.sApplication.packageManager.getApplicationInfo(
      AppGlobal.sApplication.packageName,
      flags
    )
  }

  val packageInfo =
    AppGlobal.sApplication.packageManager.getPackageInfo(AppGlobal.sApplication.packageName, 0)

  val versionCode = if (Build.VERSION.SDK_INT >= 28) {
    packageInfo.longVersionCode
  } else {
    packageInfo.versionCode.toLong()
  }

  val versionName = packageInfo.versionName

  val applicationId = packageInfo.packageName

  fun installApk(context: Context, apkFile: File) {
    val i = Intent(Intent.ACTION_VIEW)
    if (Build.VERSION.SDK_INT >= 24) {
      val uri: Uri =
        FileProvider.getUriForFile(context, "$applicationId.fileProvider", apkFile)
      i.setDataAndType(
        uri,
        "application/vnd.android.package-archive"
      )
      i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      context.startActivity(i)
    } else {
      i.setDataAndType(
        Uri.fromFile(apkFile),
        "application/vnd.android.package-archive"
      )
      i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      context.startActivity(i)
    }
  }
}
