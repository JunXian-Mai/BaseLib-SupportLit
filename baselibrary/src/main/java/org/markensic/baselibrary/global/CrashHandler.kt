package org.markensic.baselibrary.global

import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import org.markensic.baselibrary.api.utils.DisPlayUtils
import org.markensic.baselibrary.api.utils.FileKtUtils
import org.markensic.baselibrary.global.AppGlobal.Companion.sApplication
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


object CrashHandler: Thread.UncaughtExceptionHandler {
    private val path = AppGlobal.sApplication.getExternalFilesDir(null)!!.absolutePath + File.separator + "crashfolder" + File.separator
    private val systemHandler = Thread.getDefaultUncaughtExceptionHandler()

    var upLoadCrashFileListener: UploadListener? = null

    fun init() {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(p0: Thread, p1: Throwable) {
        handleException(p0, p1).also {
            if (it) {
                Thread.sleep(3000)
                Process.killProcess(Process.myPid())
                exitProcess(1)
            } else {
                systemHandler?.uncaughtException(p0, p1)
            }
        }
    }

    fun collectDeviceInfo(): String {
        return StringBuilder().also { builder ->
            sApplication.packageManager?.also { pm ->
                val packInfo = pm.getPackageInfo(sApplication.packageName, PackageManager.GET_ACTIVITIES)
                val code: Int by lazy {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        packInfo.longVersionCode.toInt()
                    } else {
                        packInfo.versionCode
                    }
                }
                builder.append("""
                    Package Information:
                    SDK: ${Build.VERSION.SDK_INT}
                    VersionName: ${packInfo.versionName}
                    VersionCode: $code${"\n\n"}
                """.trimIndent())

                builder.append("Hardware Information:\n")
                builder.append("""
                    DISPLAY: ${DisPlayUtils.physicsDm.toString().let { 
                    it.substring(it.indexOf("{") + 1, it.lastIndexOf("}"))
                }}${"\n"}
                """.trimIndent())
                Build::class.java.declaredFields.forEach {
                    it.isAccessible = true
                    builder.append("""
                        ${it.name}: ${it.get(null)}${"\n"}
                    """.trimIndent())
                }
            }
        }.toString()
    }

    private fun handleException(p0: Thread, p1: Throwable): Boolean {
        return p1.let { ex ->
            val fileName = "Crash_${SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(Date())}_${p1::class.java.simpleName}.log"
            FileKtUtils.createFile(path + fileName).also { file ->
                file.printWriter().use { writer ->
                    writer.println("""
                        Crash Time:
                        ${SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(Date())}${"\n"}           
                    """.trimIndent())
                    writer.println(collectDeviceInfo())
                    writer.println("""
                        Thread: ${p0.name}
                        Exception StackTrace:
                    """.trimIndent())
                    ex.forEarch { cause ->
                        cause.printStackTrace(writer)
                    }
                }
                upLoadCrashFileListener?.upLoadCrashFile(file)
            }
            true
        }
    }

    private fun Throwable.forEarch(function: (cause: Throwable) -> Unit) {
        this.also {
            function(it)
        }.cause?.forEarch(function)
    }

    interface UploadListener {
        fun upLoadCrashFile(f: File)
    }
}