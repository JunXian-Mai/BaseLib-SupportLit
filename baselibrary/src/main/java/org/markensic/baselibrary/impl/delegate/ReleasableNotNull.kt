package org.markensic.baselibrary.impl.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ReleasableNotNull<T: Any>: ReadWriteProperty<Any, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Not initialized or released already")
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }

    fun isInitialized() = value != null

    fun release() {
        value = null
    }
}