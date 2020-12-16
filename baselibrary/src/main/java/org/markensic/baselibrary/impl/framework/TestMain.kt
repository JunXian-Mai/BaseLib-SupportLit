package org.markensic.baselibrary.impl.framework

fun main() {
  val b = ResizableCapacityLinkedBlockIngQueue<Runnable>(1000)
  println(b.getCapacity())
  b.setCapacity(999)
  println(b.getCapacity())
}
