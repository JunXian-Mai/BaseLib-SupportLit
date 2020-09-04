package org.markensic.baselibrary.global

import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import android.os.Build
import android.os.DeadSystemException
import android.util.Log
import org.markensic.baselibrary.api.utils.LogFileUtils
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import java.lang.StringBuilder
import java.net.UnknownHostException

class AppLog {

    companion object {
        private val isDebug = AppGlobal.sApplication.applicationInfo.flags.and(FLAG_DEBUGGABLE) != 0

        var forceEnable = false

        var forceDisEnable = false

        var outputLevel = LogLevel.ASSERT

        var saveToFile = true

        private fun isEnable(): Boolean {
            LogFileUtils.checkLogFileVailTime()
            return !forceDisEnable && forceEnable || isDebug
        }

        private fun printlnLog(level: LogLevel, tag: String, message: String, tr: Throwable?, log: ()->Int): Int {
            return isEnable().let {
                val trMsg = StringBuilder()
                tr?.forEarch { ex ->
                    when (ex) {
                        is UnknownHostException -> return@forEarch
                        else -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && ex is DeadSystemException) {
                                trMsg.append("DeadSystemException: The system died; earlier logs will point to the root cause")
                                return@forEarch
                            }

                            ByteArrayOutputStream().use { baos ->
                                PrintWriter(baos).use { write ->
                                    ex.printStackTrace(write)
                                }
                                trMsg.append(baos.toString())
                            }
                        }
                    }
                }

                val logMsg = trMsg.let {
                    if (it.toString().isEmpty()) {
                        "${level.simpleTag}/$tag: $message"
                    } else {
                        "${level.simpleTag}/$tag: $message\n$it"
                    }
                }
                if (it) {
                    meetOutputLevel(level, logMsg, log)
                } else {
                    -1
                }
            }
        }

        private fun meetOutputLevel(level: LogLevel, message: String, log: ()->Int): Int {
            return outputLevel.let {
                if (it.value >= level.value) {
                    saveToFile(message, log)
                } else {
                    -1
                }
            }
        }

        private fun saveToFile( message: String, log: ()->Int): Int{
            return if (saveToFile) {
                LogFileUtils.writeToFile(message)
                log()
            } else {
                log()
            }
        }


        fun v(tag: String, message: String, tr: Throwable? = null): Int {
            return printlnLog(LogLevel.VERBOSE, tag, message, tr) {
                tr?.let {
                    Log.v(tag, message, tr)
                } ?: Log.v(tag, message)
            }
        }
        fun e(tag: String, message: String, tr: Throwable? = null): Int {
            return printlnLog(LogLevel.ERROR, tag, message, tr) {
                tr?.let {
                    Log.e(tag, message, tr)
                } ?: Log.e(tag, message)
            }
        }

        fun i(tag: String, message: String, tr: Throwable? = null): Int {
            return printlnLog(LogLevel.INFO, tag, message, tr) {
                tr?.let {
                    Log.i(tag, message, tr)
                } ?: Log.i(tag, message)
            }
        }

        fun d(tag: String, message: String, tr: Throwable? = null): Int {
            return printlnLog(LogLevel.DEBUG, tag, message, tr) {
                tr?.let {
                    Log.d(tag, message, tr)
                } ?: Log.d(tag, message)
            }
        }

        fun w(tag: String, message: String, tr: Throwable? = null): Int {
            return printlnLog(LogLevel.WARN, tag, message, tr) {
                tr?.let {
                    Log.w(tag, message, tr)
                } ?: Log.w(tag, message)
            }
        }

        fun setSaveDay(vailDay: Int){
            LogFileUtils.saveDay = vailDay
        }

        fun setLogFilePath(path: String){
            LogFileUtils.logPath = path
        }

        private fun Throwable.forEarch(function: (cause: Throwable) -> Unit) {
            this.also {
                function(it)
            }.cause?.forEarch(function)
        }
    }

    enum class LogLevel(val value: Int, val simpleTag: String) {
        VERBOSE(2, "V"),
        DEBUG(3, "D"),
        INFO(4, "I"),
        WARN(5, "W"),
        ERROR(6, "E"),
        ASSERT(7, "A");
    }
}