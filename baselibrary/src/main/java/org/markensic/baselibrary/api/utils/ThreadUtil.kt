package org.markensic.baselibrary.api.utils

import com.google.common.util.concurrent.ThreadFactoryBuilder
import java.util.concurrent.*
import kotlin.math.roundToInt

object ThreadUtil {
    val threadPoolMap: MutableMap<String, ThreadPoolExecutor> = mutableMapOf()

    val cpuCount: Int
        get() {
            return Runtime.getRuntime().availableProcessors()
        }

    fun getThreadFactory(name: String): ThreadFactory {
        return ThreadFactoryBuilder().setNameFormat("$name-task-%d").build()
    }

    fun creatCPUTaskPool(
        poolName: String,
        cpuUseTime: Int,
        ioUseTime: Int,
        interval: Int = 0,
        taskUseTimeUnit: TimeUnit = TimeUnit.MILLISECONDS,
        reject: RejectedExecutionHandler = ThreadPoolExecutor.AbortPolicy()
    ): ThreadPoolExecutor {
        return creatCommonTaskPool(
            poolName,
            TaskType.CPU,
            cpuUseTime,
            ioUseTime,
            interval,
            taskUseTimeUnit,
            reject
        )
    }

    fun creatIOTaskPool(
        poolName: String,
        cpuUseTime: Int,
        ioUseTime: Int,
        interval: Int = 0,
        taskUseTimeUnit: TimeUnit = TimeUnit.MILLISECONDS,
        reject: RejectedExecutionHandler = ThreadPoolExecutor.AbortPolicy()
    ): ThreadPoolExecutor {
        return creatCommonTaskPool(
            poolName,
            TaskType.IO,
            cpuUseTime,
            ioUseTime,
            interval,
            taskUseTimeUnit,
            reject
        )
    }

    fun creatSingleTaskPool(
        poolName: String,
        cpuUseTime: Int,
        ioUseTime: Int,
        interval: Int = 0,
        taskUseTimeUnit: TimeUnit = TimeUnit.MILLISECONDS,
        reject: RejectedExecutionHandler = ThreadPoolExecutor.AbortPolicy()
    ): ThreadPoolExecutor {
        return creatCommonTaskPool(
            poolName,
            TaskType.SINGLE,
            cpuUseTime,
            ioUseTime,
            interval,
            taskUseTimeUnit,
            reject
        )
    }

    fun creatCommonTaskPool(
        poolName: String,
        type: TaskType,
        cpuUseTime: Int,
        ioUseTime: Int,
        interval: Int = 0,
        taskUseTimeUnit: TimeUnit = TimeUnit.MILLISECONDS,
        reject: RejectedExecutionHandler = ThreadPoolExecutor.AbortPolicy()
    ): ThreadPoolExecutor {
        val taskUseTime = cpuUseTime + ioUseTime
        val coreSize = when (type) {
            TaskType.CPU -> {
                cpuCount + 1
            }
            TaskType.IO -> {
                (cpuCount * (1 + ioUseTime.toFloat() / cpuUseTime)).roundToInt()
            }
            TaskType.SINGLE -> {
                1
            }
            else -> {
                TODO("Not EXIST TASK TYPE")
            }
        }

        val concurrent = when (interval) {
            0 -> {
                taskUseTime * coreSize
            }
            else -> {
                (taskUseTime * coreSize / Math.abs(interval).toFloat()).let {
                    if (it < coreSize) {
                        coreSize
                    } else {
                        it.roundToInt()
                    }
                }
            }
        }
        val maxSize = when (type) {
            TaskType.SINGLE -> {
                1
            }
            else -> {
                concurrent
            }
        }

        val queueCount = when (type) {
            TaskType.SINGLE -> {
                interval.let {
                    when(interval) {
                        0 -> 10000 * concurrent
                        else -> 10000 / Math.abs(interval) * concurrent
                    }
                }
            }
            else -> {
                when(interval) {
                    0 -> concurrent
                    else -> concurrent * (taskUseTime / interval)
                }
            }
        }

        return threadPoolMap[poolName].let { pool ->
            pool ?: ThreadPoolExecutor(
                coreSize,
                maxSize,
                (maxSize * taskUseTime).toLong(),
                taskUseTimeUnit,
                LinkedBlockingDeque<Runnable>(queueCount),
                getThreadFactory(poolName),
                reject
            ).also {
                threadPoolMap[poolName] = it
            }
        }
    }

    enum class TaskType {
        SINGLE,
        CPU,
        IO
    }
}