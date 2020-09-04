package org.markensic.baselibrary.api.utils

import org.markensic.baselibrary.api.delegate.SharedPreferencesMapDelegate
import org.markensic.baselibrary.global.AppGlobal
import java.io.File
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

object LogFileUtils {
    private const val LOG = "_mSicLog"
    private const val LOG_PATH_KEY = "${LOG}_File_Path"
    private const val LOG_VAIL_KEY = "${LOG}_File_VAIL"
    private const val POOL_NAME = "logThreadPool"

    private val sp = SharedPreferencesMapDelegate(LOG)
    private val pool = ThreadUtils.creatSingleTaskPool(POOL_NAME, 5, 4, 1, TimeUnit.MILLISECONDS, ThreadPoolExecutor.DiscardOldestPolicy())

    private val checkValid = AtomicBoolean(false)

    private val today: String
        get() {
            return SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())
        }

    private val logFileName get() =  "$today.log"

    private val msgPrefix: String get() {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(Date())
    }

    var logPath: String
        get() {
            return sp[LOG_PATH_KEY].let {
                if (it is String) {
                    it
                } else {
                    AppGlobal.sApplication.getExternalFilesDir(null)!!.absolutePath + File.separator + "logFileFolder" + File.separator
                }
            }
        }
        set(value) {
            sp[LOG_PATH_KEY] = value
        }

    var saveDay: Int
        get() {
            return sp[LOG_VAIL_KEY].let {
                if (it is Int) {
                    it
                } else {
                    30
                }
            }
        }
        set(value) {
            sp[LOG_VAIL_KEY] = value
        }

    fun checkLogFileVailTime() {
        if (checkValid.compareAndSet(false, true)) {
            FileUtils.iterateFileInDir(logPath) { file ->
                if (file.isFile && file.name.endsWith(".log")) {
                    val logDateTime = file.name.substring(0, file.name.lastIndexOf(".log")).let { dateStr ->
                        SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(dateStr, ParsePosition(0))?.time?.also {
                            if ((Date().time - it) / (24 * 60 * 60 * 1000) > saveDay) {
                                file.delete()
                            }
                        }
                    }
                }
            }
        }
    }

    fun writeToFile(msg: String) {
        pool.execute {
            val logFilePath = "$logPath/$logFileName"
            val logText = "$msgPrefix $msg \n"
            FileUtils.appendToFile(logFilePath, logText)
        }
    }
}