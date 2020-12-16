package org.markensic.baselibrary.impl.framework

import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.concurrent.LinkedBlockingQueue


class ResizableCapacityLinkedBlockIngQueue<E>(capacity: Int) : LinkedBlockingQueue<E>(capacity) {
  val field: Field by lazy {
    this::class.java.superclass.getDeclaredField("capacity").also {
      it.isAccessible = true
      val accessFlagsField = it::class.java.getDeclaredField("accessFlags")
      accessFlagsField.isAccessible = true
      accessFlagsField.setInt(it, it.modifiers.and(Modifier.FINAL.inv()))
    }
  }

  fun setCapacity(capacity: Int) {
    field.set(this, capacity)
  }

  fun getCapacity(): Int = field.get(this) as Int
}
