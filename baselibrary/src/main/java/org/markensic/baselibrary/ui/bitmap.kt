package org.markensic.baselibrary.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArray(): ByteArray {
  val baos = ByteArrayOutputStream()
  this.compress(Bitmap.CompressFormat.PNG, 100, baos)
  return baos.toByteArray()
}

fun Bitmap.compressInWidth(targetDensity: Int): Bitmap {
  val opt = BitmapFactory.Options()
  opt.inJustDecodeBounds = true
  val bytes = this.toByteArray()
  BitmapFactory.decodeByteArray(bytes, 0, bytes.size, opt)
  opt.inJustDecodeBounds = false
  opt.inDensity = opt.outWidth
  opt.inTargetDensity = targetDensity
  return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, opt)
}
