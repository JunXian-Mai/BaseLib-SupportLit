package org.markensic.baselibrary.global

import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import android.util.Log
import org.markensic.baselibrary.api.utils.LogFileUtil

class AppLog {

    companion object {
        private val isDebug = AppGlobal.sApplication.applicationInfo.flags.and(FLAG_DEBUGGABLE) != 0

        var forceEnable = false

        var forceDisEnable = false

        var outputLevel = LogLevel.ASSERT

        var saveToFile = true

        private fun isEnable(): Boolean {
            LogFileUtil.checkLogFileVailTime()
            return !forceDisEnable && forceEnable || isDebug
        }

        private fun printlnLog(level: LogLevel, tag: String, message: String, log: ()->Int): Int {
            return isEnable().let {
                val logMsg = "${level.simpleTag}/$tag: $message"
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
                LogFileUtil.writeToFile(message)
                log()
            } else {
                log()
            }
        }


        fun v(tag: String, message: String): Int {
            return printlnLog(LogLevel.VERBOSE, tag, message) {
                Log.v(tag, message)
            }
        }
        fun e(tag: String, message: String): Int {
            return printlnLog(LogLevel.ERROR, tag, message) {
                Log.e(tag, message)
            }
        }

        fun i(tag: String, message: String): Int {
            return printlnLog(LogLevel.INFO, tag, message) {
                Log.i(tag, message)
            }
        }

        fun d(tag: String, message: String): Int {
            return printlnLog(LogLevel.DEBUG, tag, message) {
                Log.d(tag, message)
            }
        }

        fun w(tag: String, message: String): Int {
            return printlnLog(LogLevel.WARN, tag, message) {
                Log.w(tag, message)
            }
        }

        fun setSaveDay(vailDay: Int){
            LogFileUtil.saveDay = vailDay
        }

        fun setLogFilePath(path: String){
            LogFileUtil.logPath = path
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