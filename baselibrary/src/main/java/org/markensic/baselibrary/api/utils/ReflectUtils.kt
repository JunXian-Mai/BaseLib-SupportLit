package org.markensic.baselibrary.api.utils

import java.lang.reflect.Field
import java.lang.reflect.Modifier

object ReflectUtils {

  fun <T> changeFinal(clazz: Class<T>, fieldName: String): Field? {
    return try {
      val field = clazz.getDeclaredField(fieldName).also {
        it.isAccessible = true
        val accessFlagsField = it::class.java.getDeclaredField("accessFlags")
        accessFlagsField.isAccessible = true
        accessFlagsField.setInt(it, it.modifiers.and(Modifier.FINAL.inv()))
      }
      field
    } catch (e: NoSuchFieldException) {
      null
    }
  }
}
