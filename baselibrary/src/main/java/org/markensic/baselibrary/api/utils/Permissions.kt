package org.markensic.baselibrary.api.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

object Permissions {
  val readWritePermission = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
  )

  val netWorkPermission = arrayOf(Manifest.permission.INTERNET)

  val recordPermission = arrayOf(Manifest.permission.RECORD_AUDIO)

  val cameraPermission = arrayOf(Manifest.permission.CAMERA)

  val phonePermission = arrayOf(
    Manifest.permission.READ_PHONE_STATE,
    Manifest.permission.CALL_PHONE,
    Manifest.permission.READ_CALL_LOG,
    Manifest.permission.WRITE_CALL_LOG,
    Manifest.permission.USE_SIP,
    Manifest.permission.PROCESS_OUTGOING_CALLS
  )

  val locationPermission = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  )

  val smsPermission = arrayOf(
    Manifest.permission.SEND_SMS,
    Manifest.permission.RECEIVE_SMS,
    Manifest.permission.READ_SMS,
    Manifest.permission.RECEIVE_WAP_PUSH,
    Manifest.permission.RECEIVE_MMS
  )

  val contactPermission = arrayOf(
    Manifest.permission.READ_CONTACTS,
    Manifest.permission.WRITE_CONTACTS,
    Manifest.permission.GET_ACCOUNTS
  )

  val calendarPermission =
    arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)

  fun requestPermission(activity: Activity, permissions: Array<String>) {
    permissions.forEach {
      if (ActivityCompat.checkSelfPermission(
          activity,
          it
        ) != PackageManager.PERMISSION_GRANTED
      ) {
        ActivityCompat.requestPermissions(activity, permissions, 200)
        return@forEach
      }
    }
  }
}
