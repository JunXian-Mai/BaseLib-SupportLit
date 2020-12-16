package org.markensic.baselibrary.impl.framework

import java.util.concurrent.RejectedExecutionHandler
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ModifyThreadPool(
  corePoolSize: Int,
  maximumPoolSize: Int,
  keepAliveTime: Long,
  unit: TimeUnit,
  workQueue: Int,
  threadFactory: ThreadFactory,
  handler: RejectedExecutionHandler,
  private val isSingle: Boolean = false
) : ThreadPoolExecutor(
  corePoolSize,
  maximumPoolSize,
  keepAliveTime,
  unit,
  ResizableCapacityLinkedBlockIngQueue<Runnable>(workQueue),
  threadFactory,
  handler
) {

  init {
    //允许回收核心线程，实现动态修改线程池
    allowCoreThreadTimeOut(true)
  }

  override fun execute(command: Runnable?) {
    if (queue is ResizableCapacityLinkedBlockIngQueue) {
      (queue as ResizableCapacityLinkedBlockIngQueue<Runnable>).also { queue ->
        if (queue.size > queue.remainingCapacity() - corePoolSize) {
          if (!isSingle) {
            corePoolSize *= 2
            maximumPoolSize = corePoolSize
            prestartAllCoreThreads()
          }
          val c = if (isSingle) {
            queue.remainingCapacity() * queue.remainingCapacity()
          } else {
            corePoolSize * maximumPoolSize
          }
          queue.setCapacity(c)
        }
      }
    }
    super.execute(command)
  }
}
