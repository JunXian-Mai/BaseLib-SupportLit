package org.markensic.baselibrary

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
//  try {
//    runBlock()
//  } catch(e: ArithmeticException) {
//    println("Computation failed with ArithmeticException")
//  }

  asyncFun()
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
  val one = async<Int> {
    try {
      delay(Long.MAX_VALUE) // 模拟一个长时间的运算
      42
    } finally {
      println("First child was cancelled")
    }
  }
  val two = async<Int> {
    println("Second child throws an exception")
    throw ArithmeticException()
  }
  one.await() + two.await()
}

suspend fun doSomethingUsefulOne(): Int {
  delay(1000L) // 假设我们在这里做了一些有用的事
  return 13
}

suspend fun doSomethingUsefulTwo(): Int {
  delay(1000L) // 假设我们在这里也做了一些有用的事
  return 29
}

suspend fun runBlock() {
  runBlocking {
    async {
      println("start launch 1")
      delay(1000L)
      println("42")
    }
    async {
      println("start launch 2")
      delay(500L)
      throw ArithmeticException()
    }
  }
}


suspend fun asyncFun() = coroutineScope {
  val one = async {
    doSomethingUsefulOne()
  }
  val two = async {
    doSomethingUsefulTwo()
  }
  println("${one.await()} : ${two.await()}")
}
