package org.markensic.baselibrary.impl.framework

import org.markensic.baselibrary.api.utils.ReflectUtils
import java.lang.reflect.Field
import java.util.concurrent.LinkedBlockingQueue


class ResizableCapacityLinkedBlockIngQueue<E>(capacity: Int) : LinkedBlockingQueue<E>(capacity) {
  val field: Field? by lazy {
    ReflectUtils.changeFinal(this::class.java.superclass, "capacity")
  }

  fun setCapacity(capacity: Int) {
    field?.set(this, capacity)
  }

  fun getCapacity(): Int = (field?.get(this) ?: 0) as Int
}
