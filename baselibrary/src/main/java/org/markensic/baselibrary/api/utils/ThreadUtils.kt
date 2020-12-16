package org.markensic.baselibrary.api.utils

import com.google.common.util.concurrent.ThreadFactoryBuilder
import org.markensic.baselibrary.global.ActivityManager
import org.markensic.baselibrary.global.AppLog
import org.markensic.baselibrary.impl.framework.ModifyThreadPool
import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object ThreadUtils {
  val tag = this::class.java.simpleName

  val threadPoolMap: MutableMap<String, ModifyThreadPool> = mutableMapOf()

  val cpuCount: Int
    get() {
      return Runtime.getRuntime().availableProcessors()
    }

  fun getThreadFactory(name: String) =
    ThreadFactoryBuilder().setNameFormat("$name-task-%d").build()

  fun doMainThread(action: () -> Unit) {
    ActivityManager.weakCurrentActivity?.get()?.runOnUiThread {
      action()
    } ?: AppLog.d(tag, "getMainThread Error")
  }

  fun newThreadStart(action: () -> Unit) {
    Thread {
      run {
        action()
      }
    }.start()
  }

  fun createCommonThreadPool(
    poolName: String,
    keepLive: Pair<Long, TimeUnit> = 60.toLong() to TimeUnit.SECONDS,
    reject: RejectedExecutionHandler = ThreadPoolExecutor.AbortPolicy()
  ): ModifyThreadPool {
    val coreSize = cpuCount
    val maxSize = cpuCount * 2
    val queueCount = coreSize * maxSize

    return threadPoolMap[poolName].let { pool ->
      pool ?: ModifyThreadPool(
        coreSize,
        maxSize,
        keepLive.first,
        keepLive.second,
        queueCount,
        getThreadFactory(poolName),
        reject
      ).also {
        threadPoolMap[poolName] = it
      }
    }
  }

  fun createSingleThreadPool(
    poolName: String,
    keepLive: Pair<Long, TimeUnit> = 60.toLong() to TimeUnit.SECONDS,
    reject: RejectedExecutionHandler = ThreadPoolExecutor.AbortPolicy()
  ): ModifyThreadPool {
    val coreSize = 1
    val maxSize = 1
    val queueCount = coreSize * 10

    return threadPoolMap[poolName].let { pool ->
      pool ?: ModifyThreadPool(
        coreSize,
        maxSize,
        keepLive.first,
        keepLive.second,
        queueCount,
        getThreadFactory(poolName),
        reject,
        true
      ).also {
        threadPoolMap[poolName] = it
      }
    }
  }
}
