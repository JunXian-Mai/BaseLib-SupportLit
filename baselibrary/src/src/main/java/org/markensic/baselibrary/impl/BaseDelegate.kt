package org.markensic.baselibrary.impl

interface BaseDelegate {
    fun getValue(block: ()-> Any)

    fun setValue(block: ()-> Unit)
}