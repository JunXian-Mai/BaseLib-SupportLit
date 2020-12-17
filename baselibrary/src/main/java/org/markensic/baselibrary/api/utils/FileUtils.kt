package org.markensic.baselibrary.api.utils

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import org.markensic.baselibrary.global.AppGlobal
import org.markensic.baselibrary.global.AppLog
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URI
import java.nio.channels.FileChannel


object FileUtils {
  val sDefaultPath =
    AppGlobal.sApplication.getExternalFilesDir(null)!!.absolutePath + File.separator

  fun createFile(path: String): File {
    return File(path).also {
      if (!it.exists()) {
        val dirPath = path.substring(0, path.lastIndexOf("/"))
        createFileDir(dirPath)

        if (path != dirPath) {
          it.createNewFile()
        }
      }
    }
  }

  fun iterateFileInDir(path: String, handler: (f: File) -> Unit) {
    File(path).also {
      if (it.exists() && it.isDirectory) {
        it.walk().iterator().forEach { file ->
          handler(file)
        }
      }
    }
  }

  fun createFileDir(path: String): Boolean {
    return File(path).mkdirs()
  }

  fun deleteFile(path: String): Boolean {
    return File(path).let {
      if (it.exists()) {
        it.deleteRecursively()
      } else {
        true
      }
    }
  }

  fun appendToFile(path: String, text: String) {
    createFile(path).also {
      it.appendText(text)
    }
  }

  fun writeToFile(path: String, text: String) {
    createFile(path).also {
      it.writeText(String())
    }
  }

  fun writeToFile(path: String, array: ByteArray) {
    createFile(path).also {
      it.writeBytes(array)
    }
  }

  fun uriCastFile(uri: Uri): File? {
    return if (uri.scheme == ContentResolver.SCHEME_FILE) {
      uri.path?.let {
        File(it)
      }
    } else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        AppLog.d(AppGlobal.sLogTag, "uri cast to file more than N")
        val contentResolver = AppGlobal.sApplication.getContentResolver()
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
          if (it.moveToFirst()) {
            val ios = contentResolver.openInputStream(uri)
            it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
              .let { fileName ->
                val file = if (Build.VERSION.SDK_INT >= 29) {
                  AppLog.d(AppGlobal.sLogTag, "uri cast to file in Q")
                  File("${AppGlobal.sApplication.externalCacheDir!!.absolutePath}/$fileName")
                } else {
                  AppLog.d(AppGlobal.sLogTag, "uri cast to file less than Q")
                  File(AppGlobal.sApplication.filesDir, fileName)
                }
                val fcos = FileOutputStream(file).channel
                val fcin: FileChannel = (ios as FileInputStream).channel
                fcos.transferFrom(fcin, 0, fcin.size())
                file
              }
          } else {
            AppLog.d(AppGlobal.sLogTag, "this uri can not find file")
            null
          }
        }
      } else {
        AppLog.d(AppGlobal.sLogTag, "uri cast to file less than N")
        File(URI(uri.toString()))
      }
    } else {
      throw RuntimeException("uri scheme error: ${uri.scheme}")
    }
  }
}
